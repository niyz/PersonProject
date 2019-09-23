package se.experis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class databaseFunctions {

	public static void main(String[] args) throws SQLException {
		
		
		
	}
	public static String select(String sql) {
		String info = "";	
		try (Connection conn = connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
	        
			info = rs.toString();
			/*while (rs.next()) {
				info+=rs.getString("id");
	        }*/
		}
	    catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }	
		return info;
	}
	public static void insertPerson(Person person) {
		
		String personInfo = "INSERT INTO person(personID,firstName,lastName,phoneNumber,adressID) "
				+ "VALUES('"+person.getPersonID()+"','"+person.getName()+"','"+person.getLastName()+"')";
		
		executeInsertSQL(personInfo);
		
		ArrayList<String> phoneNums = person.getPhoneIDList();
		Address adress = person.getAddress();
		
		String ID = "SELECT id FROM person WHERE personID="+person.getPersonID();
		for(String num:person.getPhoneIDList()) {
			executeInsertSQL("INSERT INTO phone(personID,phone) VALUES('"+ID+"','"+num+"')");
		}
		for(String email:person.getEmailList()) {
			executeInsertSQL("INSERT INTO email(email,personID) VALUES('"+email+"','"+ID+"')");
		}
		
		
	}
	private static void executeInsertSQL(String sql) {
		Connection conn = connect();
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
