
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * manages database
 * @author Zane (18040182)
 */
public class DBManager {
    private final static String USER_NAME = "sudoku";
    private final static String PASSWORD = "sudoku";
    private final static String URL = "jdbc:derby://localhost:1527/Sudoku";
    
    Connection conn;
    
    DBManager(){
        establishConnection();
    }
    
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * initializes the database
     */
    public void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * closes database connection
     */
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
                System.err.println("Database Closed");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /**
     * gets score of a given user from the database
     * @param name of user
     * @return best score of a user
     */
    public int getScore(String name){
        int points = 81;
        ResultSet rs = queryDB("SELECT points FROM scores WHERE name = '"+name+"'");
        try{
            if(rs.next()){
                points = rs.getInt("points");
            }
        }catch (SQLException ex){
            System.err.println(ex);
        }
        return points;
    }
    
    public boolean exists(String name){
        ResultSet rs = queryDB("SELECT name FROM scores WHERE name = '"+name+"'");
        try{
            return rs.next();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        return false;
    }
    
    /**
     * sets the score in the database
     * @param name of the user
     * @param score is the number of incorrect cells
     */
    public void setScore(String name, int score){
        if(exists(name)){   // name exists
            if(score < getScore(name)){
                updateDB("UPDATE scores SET points="+score+" where name = '"+name+"'");
            }
        }
        else {              // name doesn't exist
            updateDB("INSERT INTO scores(name,points) VALUES ('"+name+"',"+score+")");
        }
    }
    
    /**
     * general DB check method
     * @param sql command to be checked against the DB
     * @return the set of results that fit the criteria
     */
    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    /**
     * updates DB with given criteria
     * @param sql criteria to change the DB
     */
    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
