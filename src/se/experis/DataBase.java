package se.experis;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public DataBase(){
    }

    public Connection dbConnect() throws SQLException{
        Connection conn = null;

        String dbUrl = "jdbc:sqlite::resource:"; // add dbname.
        conn = DriverManager.getConnection(dbUrl);
        return conn;
    }

    public void dbCreate(){ // booleand?

    }

    public ArrayList<Person> dbRead(String searchStrSql){
        Connection conn = null;
        Person p;
        Address addr = new Address("SWE","Vaxjo","Gatan","1","1234");
        ArrayList<Person> pList = new ArrayList<>();
        ArrayList<String> phlist = new ArrayList<>();
        try{
            conn = dbConnect();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(searchStrSql);
            while(result.next()){
                //do shit with the stuff..
                System.out.printf("id: %s" +
                                "\tpersonId: %s" +
                                "\tFirstName: %s" +
                                "\tLastName: %s", result.getString("id"),
                        result.getString("personid"),
                        result.getString("FirstName"),
                        result.getString("LastName"));
                p = new Person(result.getString("FirstName"),
                        result.getString("personid"),
                        result.getString("LastName"), phlist, addr);
                pList.add(p);

            }
            System.out.println("plist size: " + pList.size());
        } catch (SQLException e) {
            System.out.println("Error: failed to connect to db");
            e.printStackTrace();
        } finally {
            try{
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error: Failed to close db connection");
                e.printStackTrace();
            }
        }

        return pList;

    }

    public boolean dbPersonUpdate(String person, Person personObj){
        Connection conn = null;
        Person pObj = personObj;
        boolean isSuccessfull = false;
        try {
            conn = dbConnect();
            String updatePersonSql = "UPDATE person SET personId=?, firstName=?, lastname=?, addressId=? WHERE personId=?";
            PreparedStatement pstmt = conn.prepareStatement(updatePersonSql);
            pstmt.setString(1, pObj.getPersonID());
            pstmt.setString(2, pObj.getName());
            pstmt.setString(3, pObj.getLastName());
            pstmt.setInt(4, Integer.parseInt(pObj.getPersonID()));
            pstmt.setString(5, pObj.getPersonID());
            boolean autoCommit = conn.getAutoCommit();
            try{
                conn.setAutoCommit(false);
                pstmt.executeUpdate();
                conn.commit();
                isSuccessfull = true;
            } catch (SQLException e){
                System.out.println("Error: SQL commit failed, rolling back");
                conn.rollback();
                isSuccessfull = false;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            System.out.println("Error failed to connect to db");
            e.printStackTrace();
        } finally {
            try{
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e){
                System.out.println("Error: Failed to close db connection");
            }
        }
        return isSuccessfull;
    }

    public boolean dbEmailUpdate(String person, Person personObj){
        Connection conn = null;
        Person pObj = personObj;
        boolean isSuccessfull = false;
        try {
            conn = dbConnect();
            String updatePersonSql = "UPDATE email SET email=? WHERE personId=? AND email=?"; // check stmt
            PreparedStatement pstmt = conn.prepareStatement(updatePersonSql);
            //TODO: fix email get below?
            //pstmt.setString(1, pObj.getEmail()); // new email..
            //pstmt.setInt(2, Integer.parseInt(pObj.getPersonID())); // primary key id
            //pstmt.setString(3, pObj.getEmail()); // old email
            boolean autoCommit = conn.getAutoCommit();
            try{
                conn.setAutoCommit(false);
                pstmt.executeUpdate();
                conn.commit();
                isSuccessfull = true;
            } catch (SQLException e){
                System.out.println("Error: SQL commit failed, rolling back");
                conn.rollback();
                isSuccessfull = false;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            System.out.println("Error failed to connect to db");
            e.printStackTrace();
        } finally {
            try{
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e){
                System.out.println("Error: Failed to close db connection");
            }
        }
        return isSuccessfull;
    }

    public boolean dbDelete(String sqlStmt){

        return false;
    }

    public boolean dbAddNew(Person p){
        Connection conn = null;
        boolean isSuccessful = false;
        System.out.println("dbAddNew(Person p)");
        p.personToString();
        System.out.println();
        int rowsInserted = 0;
        String insertPersonSql = "INSERT INTO person(personID,firstName,lastName,addressid) VALUES(?,?,?,?)"; // check stmt
        //String insertAddressSql = "INSERT INTO adress(country,city,street,streetNum,postalCode) VALUES(?,?,?,?,?)";
        try {
            conn = this.dbConnect();
            /*String insertPhoneSql = "INSERT INTO phone (personID, phone) " +
                    "VALUES (?, ?)";*/
            PreparedStatement prePerson = conn.prepareStatement(insertPersonSql);
            prePerson.setString(1, p.getPersonID());
            prePerson.setString(2, p.getName());
            prePerson.setString(3, p.getLastName());
            prePerson.setInt(4,1);

            /*PreparedStatement prePhone = conn.prepareStatement(insertPhoneSql);
            prePhone.setInt(1, 1);//hämta id för person... hur?
            prePhone.setString(2, p.getPhoneIDList().get(0));*/

            /*PreparedStatement preAddr = conn.prepareStatement(insertAddressSql);
            preAddr.setString(1, p.address.getCountry());
            preAddr.setString(2, p.address.getCity());
            preAddr.setString(3, p.address.getStreet());
            preAddr.setString(4, p.address.getStreetNum());
            preAddr.setString(5, p.address.getPostalCode());*/

            boolean autoCommit = conn.getAutoCommit();
            System.out.println("autocommit: " + autoCommit);
            try{
                conn.setAutoCommit(false);

                System.out.println("Attempting to add person to database.");
                rowsInserted = prePerson.executeUpdate();
               /* System.out.println("Attempting to add persons address to db");
                preAddr.executeUpdate();*/
                System.out.println("commiting changes");
                conn.commit();
                System.out.println(rowsInserted);
                isSuccessful = true;
                System.out.println("Added Person successfully!");
            } catch (SQLException e){
                System.out.println("Error: SQL commit failed, rolling back");
                conn.rollback();
                isSuccessful = false;
            } finally {
                conn.setAutoCommit(autoCommit);
                System.out.println("in finally...");
            }
        } catch (SQLException e) {
            System.out.println("Error failed to connect to db");
            e.printStackTrace();
        } finally {
            try{
                if(conn != null){
                    conn.close();
                    System.out.println("Closing DB connection");
                }
            } catch (SQLException e){
                System.out.println("Error: Failed to close db connection");
            }
        }


        return isSuccessful;
    }

    public boolean add(Person p){
        boolean isSuccessful = false;
        String sqlAddress = "INSERT INTO address(country,city,street,streetNum,postalCode) VALUES(?,?,?,?,?)";
        String sqlPerson = "INSERT INTO person(personid,firstname,lastname,addressid) VALUES(?,?,?,?)";

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pAddr = null, pPers = null;

        try{
            conn = this.dbConnect();
            conn.setAutoCommit(false);
            pAddr = conn.prepareStatement(sqlAddress, Statement.RETURN_GENERATED_KEYS);
            pAddr.setString(1, p.address.getCountry());
            pAddr.setString(2, p.address.getCity());
            pAddr.setString(3, p.address.getStreet());
            pAddr.setString(4, p.address.getStreetNum());
            pAddr.setString(5, p.address.getPostalCode());

            int rowsAffected = pAddr.executeUpdate();
            System.out.println("pAddr: " + rowsAffected);
            rs = pAddr.getGeneratedKeys();
            int addressID = 0;
            if(rs.next()){
                addressID = rs.getInt(1);
                System.out.println("addressid: " + addressID);
            }

            if(rowsAffected != 1){
                System.out.println("Failed pAddr statement rolling back");
                conn.rollback();
            }

            //insert person
            System.out.println("Trying to insert person");
            pPers = conn.prepareStatement(sqlPerson);
            pPers.setString(1, p.getPersonID());
            pPers.setString(2, p.getName());
            pPers.setString(3, p.getLastName());
            pPers.setInt(4, addressID);

            int personRows = pPers.executeUpdate();

            System.out.println("person rows: " + personRows);
            if(personRows != 1){
                System.out.println("pPers statment failed rolling back");
                conn.rollback();

            }
            System.out.println("Commiting changes to the database.");
            conn.commit();
            System.out.println("Commit finished");
            isSuccessful = true;

        } catch (SQLException e){
            try{
                if(conn != null){
                    conn.rollback();
                    System.out.println("ERROR: SQLException conn != null rolling back.");
                }
            } catch (SQLException e2){
                System.out.println("ERROR: rollback failed");
                System.out.println(e2.getMessage());
            }
            System.out.println("Error on sql exept. while trying to commit statements?");
            System.out.println(e.getMessage());
        } finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if(pAddr != null){
                    pAddr.close();
                }
                if(pPers != null){
                    pPers.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e){
                System.out.println("rs,pAddr,pPers or conn is not null and had to be closed");
                System.out.println(e.getMessage());
            }
        }

        return isSuccessful;
    }

    public ArrayList<Person> dbSearch(String searchStr){
        ArrayList<Person> personList = new ArrayList<>();



        return personList;
    }
}
