package se.experis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseFunctions {

	public static void main(String[] args) throws SQLException {
		
		insert("INSERT INTO person(firstName,lastName)");
		
	}
	public static void select(String sql) {
			try (Connection conn = connect();
				Statement stmt  = conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
	            
	            
	            while (rs.next()) {
	                System.out.print("Country: "+rs.getString("country")+", City: "+rs.getString("city")+", Street: "+rs.getString("street")+"\n");
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}
	public static void insert(String sql) {
		Connection conn = connect();
		
		//String sql = "INSERT INTO adress(country,city,street,streetNum,postalCode) VALUES('Sweden','Växjö','Storgatan','1','35246')";
		
		boolean autoCommit = false;
		try{
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			Statement pstmt = conn.createStatement();
			pstmt.executeUpdate(sql);
        } 
		catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
		finally {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Connection connect() {
		String url = "jdbc:sqlite::resource:Task17DB.db";
        
		Connection conn=null;// create a connection to the database
        try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        System.out.println("Connection to SQLite has been established.");
        
        return conn;
    }
}
