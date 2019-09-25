package se.experis;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public DataBase(){
    }

    public Connection dbConnect() throws SQLException{
        Connection conn = null;

        try {
            String dbUrl = "jdbc:sqlite::resource:Task17DB.db"; // add dbname.
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean dbDelete(Person pObj){

        return false;
    }

    public void dbUpdate(Person pObj){

    }

    /**
     * 1 = dad
     * 2 = mom
     * 3 = son
     * 4 = daughter
     * 5 = brother
     * 6 = sister
     */

    public void addRelation(Person pObj, String otherPnummer, int relation){

    }

    public ArrayList<Person> dbSearch(String searchStr){ // search for phone number or email address dont work...
        /*ArrayList<Person> personList = new ArrayList<>();
        ArrayList<String> mailList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();
        String searchPersonSql = "SELECT * FROM person WHERE firstname LIKE ? OR lastname LIKE ?";
        String searchAddressSql= "SELECT * FROM adress WHERE adressid = ?";
        String searchPhoneSql = "SELECT * FROM phone WHERE personid = ?";
        String searchEmailSql = "SELECT * FROM email WHERE personid = ?";
        String searhcRelSql = "SELECT * FROM relationship WHERE person1 = ? OR person2 = ?";
        Connection conn = null;
        Person pObj;
        Address addr = null;
        try {
            conn = this.dbConnect();
            PreparedStatement prePersonSearch = conn.prepareStatement(searchPersonSql, Statement.RETURN_GENERATED_KEYS);
            prePersonSearch.setString(1, "%" + searchStr + "%");
            prePersonSearch.setString(2, "%" + searchStr + "%");
            ResultSet personResult = prePersonSearch.executeQuery();
            int personID = personResult.getInt("adressid");

            PreparedStatement preAddrSearch = conn.prepareStatement(searchAddressSql, Statement.RETURN_GENERATED_KEYS);
            preAddrSearch.setInt(1, personID);
            ResultSet addrResult = preAddrSearch.executeQuery();
            while(addrResult.next()){ // add address to address object that then is beeing put into person object..
                addr = new Address(
                        addrResult.getString("country"),
                        addrResult.getString("city"),
                        addrResult.getString("street"),
                        addrResult.getString("streetNum"),
                        addrResult.getString("postalcode")
                        );
            }

            PreparedStatement prePhoneSearch = conn.prepareStatement(searchPhoneSql);
            prePhoneSearch.setInt(1, personID);
            ResultSet phoneResult = prePhoneSearch.executeQuery();
            while(phoneResult.next()){
                phoneList.add(phoneResult.getString("phone"));
            }

            PreparedStatement preEmailSearch = conn.prepareStatement(searchEmailSql);
            preEmailSearch.setInt(1, personID);
            ResultSet emailResult = preEmailSearch.executeQuery();

            while(emailResult.next()){
                mailList.add(emailResult.getString("email"));
            }

            while(personResult.next()){
                pObj = new Person(
                        personResult.getString("firstname"),
                        personResult.getString("personid"),
                        personResult.getString("lastname"),
                        phoneList,
                        mailList,
                        addr
                        );

                personList.add(pObj);

            }


        } catch (SQLException e){
            System.out.println("Error: SQL Exception");
            System.out.println(e.getMessage());
        }

        return personList;*/
        ArrayList<Person> pList = new ArrayList<>();
        if(this.isInt(searchStr)){
            pList =this.dbPhonePersonSearch(searchStr);
        } else {
            pList= this.dbPersonSearch(searchStr);
        }
        return pList;
    }
    private ArrayList<Person> dbPersonSearch(String searchStr){
        ArrayList<Person> personList = new ArrayList<>();
        ArrayList<String> mailList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();
        String searchPersonSql = "SELECT * FROM person WHERE firstname LIKE ? OR lastname LIKE ?";
        String searchAddressSql= "SELECT * FROM adress WHERE adressid = ?";
        String searchPhoneSql = "SELECT * FROM phone WHERE personid = ?";
        String searchEmailSql = "SELECT * FROM email WHERE personid = ?";
        String searhcRelSql = "SELECT * FROM relationship WHERE person1 = ? OR person2 = ?";
        Connection conn = null;
        Person pObj;
        Address addr = null;

        try {
            conn = this.dbConnect();
            PreparedStatement prePersonSearch = conn.prepareStatement(searchPersonSql, Statement.RETURN_GENERATED_KEYS);
            prePersonSearch.setString(1, "%" + searchStr + "%");
            prePersonSearch.setString(2, "%" + searchStr + "%");
            ResultSet personResult = prePersonSearch.executeQuery();
            /*while(personResult.next()){
                System.out.println("result: " + personResult.getInt("id"));
            }
            int personID = personResult.getInt("adressid");
            /*ResultSet rs = customerPs.getGeneratedKeys();
            while(rs.next()){
                System.out.println("Result Set: " + rs.toString());
                customerID = rs.getInt(1);
            }*/

            /*PreparedStatement preAddrSearch = conn.prepareStatement(searchAddressSql, Statement.RETURN_GENERATED_KEYS);
            preAddrSearch.setInt(1, personResult.getInt("adressid"));
            ResultSet addrResult = preAddrSearch.executeQuery();
            while(addrResult.next()){ // add address to address object that then is beeing put into person object..
                addr = new Address(
                        addrResult.getString("country"),
                        addrResult.getString("city"),
                        addrResult.getString("street"),
                        addrResult.getString("streetNum"),
                        addrResult.getString("postalcode")
                );
            }

            PreparedStatement prePhoneSearch = conn.prepareStatement(searchPhoneSql);
            prePhoneSearch.setInt(1, personID);
            ResultSet phoneResult = prePhoneSearch.executeQuery();
            while(phoneResult.next()){
                phoneList.add(phoneResult.getString("phone"));
            }

            PreparedStatement preEmailSearch = conn.prepareStatement(searchEmailSql);
            preEmailSearch.setInt(1, personID);
            ResultSet emailResult = preEmailSearch.executeQuery();

            while(emailResult.next()){
                mailList.add(emailResult.getString("email"));
            }*/

            while(personResult.next()){

                PreparedStatement preAddrSearch = conn.prepareStatement(searchAddressSql, Statement.RETURN_GENERATED_KEYS);
                preAddrSearch.setInt(1, personResult.getInt("adressid"));
                ResultSet addrResult = preAddrSearch.executeQuery();
                while(addrResult.next()){ // add address to address object that then is beeing put into person object..
                    addr = new Address(
                            addrResult.getString("country"),
                            addrResult.getString("city"),
                            addrResult.getString("street"),
                            addrResult.getString("streetNum"),
                            addrResult.getString("postalcode")
                    );
                }

                PreparedStatement prePhoneSearch = conn.prepareStatement(searchPhoneSql);
                prePhoneSearch.setInt(1, personResult.getInt("id"));
                ResultSet phoneResult = prePhoneSearch.executeQuery();
                while(phoneResult.next()){
                    phoneList.add(phoneResult.getString("phone"));
                }

                PreparedStatement preEmailSearch = conn.prepareStatement(searchEmailSql);
                preEmailSearch.setInt(1, personResult.getInt("id"));
                ResultSet emailResult = preEmailSearch.executeQuery();

                while(emailResult.next()){
                    mailList.add(emailResult.getString("email"));
                }


                pObj = new Person(
                        personResult.getString("firstname"),
                        personResult.getString("personid"),
                        personResult.getString("lastname"),
                        phoneList,
                        mailList,
                        addr
                );
                personList.add(pObj);


            }


        } catch (SQLException e){
            System.out.println("Error: SQL Exception");
            System.out.println(e.getMessage());
        }

        return personList;
    }

    private ArrayList<Person> dbPhonePersonSearch(String searchStr){
        ArrayList<Person> personList = new ArrayList<>();
        ArrayList<String> mailList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();
        String searchPersonSql = "SELECT * FROM person WHERE id = ?";
        String searchAddressSql= "SELECT * FROM adress WHERE adressid = ?";
        String searchPhoneSql = "SELECT * FROM phone WHERE phone LIKE ?";
        String searchEmailSql = "SELECT * FROM email WHERE personid = ?";
        String searhcRelSql = "SELECT * FROM relationship WHERE person1 = ? OR person2 = ?";
        Connection conn = null;
        Person pObj;
        Address addr = null;
        try {
            conn = this.dbConnect();

            PreparedStatement prePhoneSearch = conn.prepareStatement(searchPhoneSql);
            prePhoneSearch.setString(1, "%" + searchStr + "%");
            ResultSet phoneResult = prePhoneSearch.executeQuery();
            System.out.println(phoneResult.getString("phone"));
            while(phoneResult.next()){
                phoneList.add(phoneResult.getString("phone"));
                //System.out.println(phoneResult.getString("phone"));
            }


            /*PreparedStatement prePersonSearch = conn.prepareStatement(searchPersonSql, Statement.RETURN_GENERATED_KEYS);
            prePersonSearch.setString(1, "%" + searchStr + "%");
            prePersonSearch.setString(2, "%" + searchStr + "%");
            ResultSet personResult = prePersonSearch.executeQuery();
            int personID = personResult.getInt("adressid");

            PreparedStatement preAddrSearch = conn.prepareStatement(searchAddressSql, Statement.RETURN_GENERATED_KEYS);
            preAddrSearch.setInt(1, personID);
            ResultSet addrResult = preAddrSearch.executeQuery();
            while(addrResult.next()){ // add address to address object that then is beeing put into person object..
                addr = new Address(
                        addrResult.getString("country"),
                        addrResult.getString("city"),
                        addrResult.getString("street"),
                        addrResult.getString("streetNum"),
                        addrResult.getString("postalcode")
                );
            }



            PreparedStatement preEmailSearch = conn.prepareStatement(searchEmailSql);
            preEmailSearch.setInt(1, personID);
            ResultSet emailResult = preEmailSearch.executeQuery();

            while(emailResult.next()){
                mailList.add(emailResult.getString("email"));
            }

            while(personResult.next()){
                pObj = new Person(
                        personResult.getString("firstname"),
                        personResult.getString("personid"),
                        personResult.getString("lastname"),
                        phoneList,
                        mailList,
                        addr
                );

                personList.add(pObj);

            }*/


        } catch (SQLException e){
            System.out.println("Error: SQL Exception");
            System.out.println(e.getMessage());
        }

        return personList;
    }


    /**
     * dbSearch help method
     */
    private boolean isInt(String str){
        try{
            Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.out.println("String is not an int. ");
            return false;
        }
        return true;
    }


    /**
     * Elliot shit
     */
    public void insertPerson(Person person) throws SQLException{

        Connection conn = this.dbConnect();

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

    /**
     * Help methods to insertPerson
     */

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
}
