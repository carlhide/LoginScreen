package application;

import java.sql.*;
import java.util.ArrayList;

public  class Database {

	private Connection conn;
	private String username, password, ipAdress, database;

	/** Constructor
	 * 
	 * @param ipAdress
	 * @param database
	 * @param username
	 * @param password
	 */
	public Database(String ipAdress, String database, String username, String password) {
		this.username = username;
		this.password = password;
		this.ipAdress = ipAdress;
		this.database = database;
		//		conn = null;
	}

	/** Opens connection to database
	 * 
	 * @return true if connection to database is established, false if not.
	 * @throws ClassNotFoundException
	 */

	public boolean openConnection() throws ClassNotFoundException {
		System.out.println("Connecting to database...");
		try {

			conn = DriverManager.getConnection(
					"jdbc:mysql://" + ipAdress + "/" + database +"?useSSL=false", username, password);

			// ?useSSL=false&characterEncoding=utf8&profileSQL=true

		} catch (SQLException e) {
			System.out.println("Failed to connect to database: SQLException.");

			e.printStackTrace();
			return false;
		}
		System.out.println("Connected to database!");
		return true;
	}

	/**
	 *  Closes connection to database.
	 */

	public void closeConnection() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;

		System.err.println("Database connection closed.");
	}

	/**
	 * 
	 * @return true if connection to database is up
	 */

	public boolean isConnected() {
		return conn != null;
	}

	/**
	 * 
	 * @param query to execute
	 * @return answere from database in one string
	 * @throws ClassNotFoundException
	 */

	public String executeQuery(String query) throws ClassNotFoundException {

		Statement stmt = null;
		StringBuilder sb = new StringBuilder();

		try {

			openConnection();

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			int nbrOfColumns = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= nbrOfColumns; i++) {

					sb.append(rs.getString(i));
				}
			}

			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
			return "SQLException";

		} finally {
			closeConnection();
		}
	}
	/** Executes query without opening or closeing connection prior to sending.
	 * 
	 * @param query to execute
	 * @return answere from db 
	 * @throws ClassNotFoundException
	 */
	public String executeQueryNoOpenNoClose(String query) throws ClassNotFoundException {

		Statement stmt = null;
		StringBuilder sb = new StringBuilder();

		try {


			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			int nbrOfColumns = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= nbrOfColumns; i++) {

					sb.append(rs.getString(i));
				}
			}

			return sb.toString();

		} catch (SQLException e) {

			return "SQLException";

		}

	}

	/**
	 * 
	 * @param query to execute
	 * @return true if succesful, false if not
	 * @throws ClassNotFoundException
	 */

	public boolean updateQuery(String query) throws ClassNotFoundException {

		Statement stmt = null;

		System.out.println("Running updateQuery in DB with query:" + query);

		try {

			openConnection();

			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;

		} catch (SQLException e) {

			System.out.println("SQLException in DB.updateQuery" + e.getMessage());
			return false;

		} finally {
			closeConnection();
		}
	}

	/**
	 * 
	 * Executes SQL-query and saves answere in Arraylist-matrix
	 * with first row as column names.
	 * 
	 * Example:
	 * 1. custId, custName, phonenumber, adress
	 * 2. 12345, Johan Karlsson, 0735045479, Norra Kustvägen 52
	 * 3. 51341, Henrik Larsson, 046291111, Talltitevägen 11
	 * 
	 * @param SQL-SYNTAX (SELECT * FROM Customer
	 * @return ArrayList<ArrayList<String>> tableMatrix
	 */

	public ArrayList<ArrayList<String>> getTable(String condition){

		ArrayList<ArrayList<String>> tableMatrix = new ArrayList<ArrayList<String>>();
		ArrayList<String> colNames = new ArrayList<String>();
		Statement stmt = null;

		try {
			openConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String query = condition;
			// ResultS
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			int nbrOfCols = rs.getMetaData().getColumnCount();
			for (int i = 1; i <= nbrOfCols; i++) {
				colNames.add(rs.getMetaData().getColumnLabel(i));
			}
			System.out.print(colNames.toString() + "\n");

			tableMatrix.add(colNames);

			ArrayList<String> colValues = new ArrayList<String>();

			int row = 1;

			while (rs.next()) {

				System.out.println("\nRow number " + row);
				System.out.print(colValues.toString());
				colValues.clear();

				for (int col = 1; col <= nbrOfCols; col++) {
					System.out.println(rs.getString(col));
					colValues.add(rs.getString(col));
				}


				tableMatrix.add(new ArrayList<String>(colValues));
				row++;

			}

			System.out.println("Array print: \n " + tableMatrix.toString());

			System.out.println("Retrieved table successfully");
			return tableMatrix;


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
			return null;
		} finally {
			closeConnection();
		}
	}

}