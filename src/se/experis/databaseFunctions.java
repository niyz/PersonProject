package se.experis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class databaseFunctions {

	public static void main(String[] args) {
		
		/*
		ArrayList<String> phoneNumbers = new ArrayList<String>();
		phoneNumbers.add("0703333333");
		phoneNumbers.add("0708888888");
		Address address = new Address("Sweden","Växjö","Storgatan","2","35263");
		
		Person person = new Person("Sven","199012111337","Johansson",phoneNumbers,address);
		
		insertPerson(person);*/
		selectPerson("SELECT * FROM person");
		selectAdress("SELECT * FROM adress");
		
		
	}
	public static void selectAdress(String sql) {	
		Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
                System.out.println(rs.getString("adressID")+" "+rs.getString("street")+" "+rs.getString("streetNum"));
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void selectPerson(String sql) {	
		Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
                System.out.println(rs.getString("id")+" "+rs.getString("personID")+" "+rs.getString("firstName"));
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String selectAdressID(Connection conn, String sql) {
		String info = "";	
		//Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//info=rs.getString("adressID");
			while (rs.next()) {
                info=rs.getString("adressID");
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		return info;
	}
	public static String selectAnID(Connection conn, String sql) {
		String info = "";	
		//Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			info=rs.getString("id");
			/*while (rs.next()) {
                info=rs.getString("id");
            }*/
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		return info;
	}
	public static void insertPerson(Person person) {
		
		Connection conn = connect();
		
		int adressID = getAddressIdOrAdd(conn,person.getAddress());
		
		String personInfo = "INSERT INTO person(personID,firstName,lastName,adressID) "
				+ "VALUES('"+person.getPersonID()+"','"+person.getName()+"','"+person.getLastName()+"','"+adressID+"')";
		
		executeInsertSQL(conn,personInfo);
		
		String getIDString = "SELECT id FROM person WHERE personID='"+person.getPersonID()+"'";
		
		String ID = selectAnID(conn,getIDString);
		
		for(String num:person.getPhoneIDList()) {
			executeInsertSQL(conn,"INSERT INTO phone(personID,phone) VALUES('"+ID+"','"+num+"')");
		}
		
		for(String email:person.getEmailList()) {
			executeInsertSQL(conn,"INSERT INTO email(email,personID) VALUES('"+email+"','"+ID+"')");
		}
	}
	private static int getAddressIdOrAdd(Connection conn, Address address) {
		
		String getAddressIdString = "SELECT adressID FROM adress WHERE country='"+address.getCountry()+
				"' AND city='"+address.getCity()+"' AND street='"+address.getStreet()+
				"' AND streetNum='"+address.getStreetNum()+"' AND postalCode='"+address.getPostalCode()+"'";
		
		String addressID = selectAdressID(conn,getAddressIdString);
		if(addressID.equals("")) {
			String insertAddress = "INSERT INTO adress(country,city,street,streetNum,postalCode) VALUES('"+
					address.getCountry()+"','"+address.getCity()+"','"+address.getStreet()+"','"+address.getStreetNum()+"','"+
					address.getPostalCode()+"')";
			executeInsertSQL(conn,insertAddress);
			addressID = selectAdressID(conn,getAddressIdString);
		}
		int result = Integer.parseInt(addressID);
		return result;
	}
	private static void executeInsertSQL(Connection conn, String sql) {
		//Connection conn = connect();
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
