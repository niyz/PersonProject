package se.experis;

import javax.xml.transform.Result;
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

    public boolean dbUpdate(String sqlStmt){

        return false;
    }

    public boolean dbDelete(String sqlStmt){

        return false;
    }
}
