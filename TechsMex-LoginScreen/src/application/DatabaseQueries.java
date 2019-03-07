package application;

public class DatabaseQueries {

	private Database db;

	public DatabaseQueries(Database db) {
		this.db = db;

	}

	/*
	 * UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE
	 * condition;
	 */

	public void alterValue(String table, String column1, String value1, String condition) {

		String query = "UPDATE " + table + "\n" + "	SET " + column1 + " = " + value1 + "\n" + "	WHERE " + condition
				+ ";";

		try {
			db.executeQuery(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String getCondition(String column, String table, String condition) {

		String condReturn = null;

		String query = "SELECT " + column + "FROM " + table + "WHERE '" + condition + "'";

		try {
			condReturn = db.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return condReturn;
	}
	
	/**
	 * SELECT AccountPassword FROM Accounts WHERE AccountName = '" + username + "';
	 * @param username
	 * @param password to control if correct
	 * @return true if correct password, false if not
	 */

	public boolean checkLogin(String username, String password) {

		String query = "SELECT AccountPassword FROM Accounts WHERE AccountName = '" + username + "';";
		String dbPassword = "none retrieved from database";

		try {

			dbPassword = db.executeQuery(query);
			System.out.println(dbPassword);

			if (dbPassword.equals(password)) {
				return true;

			} else {
				return false;
			}

		} catch (Exception e) {

			System.out.println("Exception: " + e.getMessage());

			return false;
		}
	}
	
	/** Update password to user
	 * 
	 * @param accountName
	 * @param accountPassword
	 * @return
	 */

	public boolean updatePassword(String accountName, String accountPassword) {

		System.out.println("Running update password with AccountName = " + accountName + " and accountPassword = "
				+ accountPassword);

		try {

			db.updateQuery("UPDATE Accounts SET AccountPassword = '" + accountPassword + "' WHERE AccountName = '"
					+ accountName + "';");

			return true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

			return false;
		}
	}
	
	/** Add row to table with query
	 *  INSERT INTO table (columns) VALUES (values);
	 * 
	 * @param table
	 * @param columns
	 * @param values
	 * @return
	 */

	public boolean addRow(String table, String[] columns, String[] values) {
		
		if(columns.length != values.length) {
			System.out.println("Values not equal to columns");
			return false;
		}

		StringBuilder columnsString = new StringBuilder();
		StringBuilder valuesString = new StringBuilder();
		String SQLQuery;

		for(String s: columns) {
			if(s.equals(columns[columns.length-1])) {
				columnsString.append(s);
			}else {
				columnsString.append(s + ", ");
			}
		}
		
		for(String s: values) {
			if(s.equals(values[values.length-1])) {
				valuesString.append("'"+s+"'");
			}else {
				valuesString.append("'"+s+"'" + ", ");
			}
		}		
		
				System.out.println("Adding row with query: " + "INSERT INTO "+ table + "(" + columnsString.toString() + ")" +
						   " VALUES (" + valuesString.toString() + ")");
				
				SQLQuery = "INSERT INTO "+ table + "(" + columnsString.toString() + ")" +
						   " VALUES (" + valuesString.toString() + ")";
				
				try {
					db.updateQuery(SQLQuery);
					return true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return false;
				}

	}

	public void sendQuery(String query) {

		try {
			db.updateQuery(query);
		} catch (ClassNotFoundException e) {
			System.out.println("Exception in sendQuery");
			e.printStackTrace();
		}

	}
	
	public String getItems(String query) {

		String condReturn = null;

		try {
			condReturn = db.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return condReturn;
	}
}


