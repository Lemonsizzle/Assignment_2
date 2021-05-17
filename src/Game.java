/**
 * main class for starting the GUI
 * @author Zane (18040182)
 */
public class Game implements CommonVariables {
    
    public static void main(String[] args) {
        GameSystem frame;
        frame = new GameSystem("Sudoku");
        frame.pack();
        frame.setVisible(true);
    }
}
