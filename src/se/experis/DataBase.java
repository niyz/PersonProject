package se.experis;

import java.sql.*;

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

    public void dbRead(String searchStrSql){
        Connection conn = null;
        try{
            conn = dbConnect();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(searchStrSql);
            while(result.next()){
                //do shit with the stuff..
            }
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

    }

    public boolean dbPersonUpdate(String person, Person personObj){
        Connection conn = null;
        Person pObj = personObj;
        boolean isSuccessfull = false;
        try {
            conn = dbConnect();
            String updatePersonSql = "UPDATE person SET personId=?, firstName=?, lastname=?, addressId=?";
            PreparedStatement pstmt = conn.prepareStatement(updatePersonSql);
            pstmt.setString(1, pObj.getPersonID());
            pstmt.setString(2, pObj.getName());
            pstmt.setString(3, pObj.getLastName());
            pstmt.setInt(4, Integer.parseInt(pObj.getPersonID()));
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
}
