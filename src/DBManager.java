
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
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

    //Establish connection
    public void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void scoreDB(String name, int score){
        ResultSet rs = queryDB("SELECT name FROM scores WHERE name = '"+name+"'");
        try {
            if(rs.next()){   // name exists
                System.err.println("name exists");
                updateDB("UPDATE INTO scores WHERE name = '"+name+"' AND points = "+score+")");
            }
            else{    // name doesn't exist
                System.err.println("no name");
                updateDB("INSERT INTO scores(name,points) VALUES ('"+name+"',"+score+")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
