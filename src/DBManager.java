
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
        int points = 0;
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
    
    /**
     * sets the score in the database
     * @param name of the user
     * @param score is the number of incorrect cells
     */
    public void setScore(String name, int score){
        ResultSet rs = queryDB("SELECT name FROM scores WHERE name = '"+name+"'");
        try {
            if(rs.next()){   // name exists
                System.err.println("name");
                if(score < getScore(name)){
                    updateDB("UPDATE scores SET points="+score+" where name = '"+name+"'");
                }
            }
            else {           // name doesn't exist
                updateDB("INSERT INTO scores(name,points) VALUES ('"+name+"',"+score+")");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * general DB check method
     * @param sql command to be checked against
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
     * 
     * @param sql criteria to 
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
