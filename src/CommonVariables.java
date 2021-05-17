import java.awt.Font;
import java.util.HashMap;

/**
 * interface containing all variables used across multiple classes
 * @author Zane (18040182)
 */
public interface CommonVariables {
    // changing text color for ease of use
    // used in board and rules
    final String NORMAL = "\u001B[0m";  // makes text white
    final String DEF_NUM = "\u001B[32m";    // makes text green (to show core cells)
    
    // used in rules
    final String SUDRULE = "\u001B[36m"; // makes text cyan (to show sudoku rules)
    final String RULE = "\u001B[33m";    // makes text yellow (to show rules)
    
    // used in sudoku, board, original,
    HashMap axis = new HashMap();
    final int MAX = 9;
    final int MAX_ROOT = (int) Math.sqrt(MAX);
    Font font = new Font("Calibri", Font.PLAIN, 20);
    
    final DBManager DBM = new DBManager();
}
