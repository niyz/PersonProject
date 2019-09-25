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
		ArrayList<String> email = new ArrayList<String>();
		email.add("Sven420@hotmail.com");
		email.add("svenNyMail@hotmail.com");
		ArrayList<String> phoneNums = new ArrayList<String>();
		phoneNums.add("0742222222");
		phoneNums.add("0742222233");
		Address adress = new Address("Israel","Los Angeles","Storgatan","420","12345");
		
		Person oldPerson = new Person("Sven","199013111337","Urbansson",phoneNums,email,adress);
		Person newPerson = new Person("Sven","199909098888","Urbansson",phoneNums,email,adress);
		
		
		//updatePerson(oldPerson,newPerson);
		addRelation(oldPerson,newPerson,1);
		*/
		
	}
	public static void addRelation(Person p1, Person p2, int typeOfRelation) {
		Connection conn = connect();
		String id1 = selectAnID(conn,"SELECT id FROM person WHERE personID='"+p1.getPersonID()+"'");
		String id2 = selectAnID(conn,"SELECT id FROM person WHERE personID='"+p2.getPersonID()+"'");
		
		
		String addRelation = "INSERT INTO relationship(person1,person2,relation) VALUES('"+id1+"','"+id2+"','"+typeOfRelation+"')";
		
		executeInsertSQL(conn,addRelation);
	}
	public static void deleteRelations(Person person) {
		Connection conn = connect();
		String id = selectAnID(conn,"SELECT id FROM person WHERE personID='"+person.getPersonID()+"'");
		String deleteRelation = "DELETE FROM relationship WHERE person1='"+id+"' OR person2='"+id+"'";
		executeInsertSQL(conn,deleteRelation);
	}
	
	
	public static void updatePerson(Person oldPerson, Person newPerson) {
		Connection conn = connect();
		
		int addressID = getAddressIdOrAdd(conn,newPerson.getAddress());
		
		String selectPersonSQL = "SELECT id FROM person WHERE personID='"+oldPerson.getPersonID()+"'";
		
		String ID = selectAnID(conn,selectPersonSQL);
		
		String executeUpdateString = "UPDATE person SET personID='"+newPerson.getPersonID()+"', firstName='"+newPerson.getName()
		+"', lastName='"+newPerson.getLastName()+"', adressID='"+addressID+"' WHERE id='"+ID+"'";
		
		
		System.out.println(executeUpdateString);
		
		executeInsertSQL(conn,executeUpdateString);
		
		
		ArrayList<String> selectAllId = selectAllId(conn,"SELECT id FROM phone WHERE personID='"+ID+"'");
		
		
		int oldSize = selectAllId.size();
		int newSize = newPerson.getPhoneIDList().size();
		for(int i=0;i<oldSize;i++) {
			executeInsertSQL(conn,"UPDATE phone SET phone='"+newPerson.getPhoneIDList().get(i)+"' WHERE id='"+selectAllId.get(i)+"'");
		}
		
		for(int i=oldSize;i<newSize;i++) {
			executeInsertSQL(conn,"INSERT INTO phone(personID,phone) VALUES('"+ID+"','"+newPerson.getPhoneIDList().get(i)+"')");
		}
		
		
		selectAllId = selectAllId(conn,"SELECT id FROM email WHERE personID='"+ID+"'");
		oldSize = selectAllId.size();
		newSize = newPerson.getEmailList().size();
		for(int i=0;i<oldSize;i++) {
			executeInsertSQL(conn,"UPDATE email SET email='"+newPerson.getEmailList().get(i)+"' WHERE id='"+selectAllId.get(i)+"'");
		}
		for(int i=oldSize;i<newSize;i++) {
			executeInsertSQL(conn,"INSERT INTO email(email,personID) VALUES('"+newPerson.getEmailList().get(i)+"','"+ID+"')");
		}
		
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
	
	
	private static ArrayList<String> selectAllId(Connection conn, String sql) {
		ArrayList<String> ids = new ArrayList<String>();	
		//Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//info=rs.getString("adressID");
			while (rs.next()) {
                ids.add(rs.getString("id"));
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		return ids;
	}
	private static String selectAdressID(Connection conn, String sql) {
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
	private static String selectAnID(Connection conn, String sql) {
		String info = "";
		//Connection conn = connect();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			//info=rs.getString("id");
			while (rs.next()) {
                info=rs.getString("id");
            }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return info;
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
