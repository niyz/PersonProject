package se.experis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseFunctionsDelete {

    public static void main(String[] args) {

        ArrayList<String> email = new ArrayList<String>();
        email.add("Sven4aaaadasdasdsadasd20@hotmail.com");
        email.add("svenNyMdasdaasdasdadsil@hotmail.com");
        ArrayList<String> phoneNums = new ArrayList<String>();
        phoneNums.add("07422123211");
        phoneNums.add("07422223212");
        Address adress = new Address("Israel","Los Angeles","Storgatan","420","12345");

        Person oldPerson = new Person("Sven","199909098181","Urbansson",phoneNums,email,adress);
        Person newPerson = new Person("Sven","197702136541","Urbansson",phoneNums,email,adress);


        //updatePerson(oldPerson,newPerson);
        //insertPerson(oldPerson);
        deletePerson(oldPerson);


    }

    public static void deletePerson(Person person){

        Connection conn = connect();
        String selectPersonSQL = "SELECT id FROM person WHERE personID='"+person.getPersonID()+"'";

        String ID = selectAnID(conn,selectPersonSQL);
        System.out.println("PHONE: ");
        String sqlStatementPhone = "DELETE FROM phone WHERE personID='"+ID+"'";
        //selectAllId(conn,sqlStatement);
        executeInsertSQL(conn, sqlStatementPhone);
        System.out.println("MAIL: ");

        String sqlStatementMail = "DELETE FROM email WHERE personID='"+ID+"'";
        executeInsertSQL(conn, sqlStatementMail);

        //TODO: Ta bort relation
        //String sqlStatementRelation = "DELETE FROM relationship WHERE personID='"+id+"'";
        //TODO: Ta bort människan



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


