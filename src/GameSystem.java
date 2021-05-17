
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * the frame class for the Sudoku GUI
 * @author Zane (18040182)
 */
public class GameSystem extends JFrame implements ActionListener, CommonVariables {
    // game panel
    private final Board board;
    private final JPanel[][] chunk;
    private final JComboBox[][] cell;
    
    // user panel
    private final JLabel userLabel;
    private final JTextField nameField;
    private final JButton loginB;
    private final JLabel points;
    
    // button panel
    private final JButton newGameB, resetB, submitB;
    
    // variables
    private long start;
    private String name = "";
    
    /**
     * manages the GUI for the Sudoku game
     * @param title of the frame
     */
    GameSystem(String title){
        super(title);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                DBM.closeConnections();
            }
        });
        
        RulesPanel rules = new RulesPanel();
        
        start = System.currentTimeMillis();
        board = new Board();
        
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(MAX_ROOT, MAX_ROOT, 20, 20));
        chunk = new JPanel[MAX_ROOT][MAX_ROOT];
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                chunk[j][i] = new JPanel();
                chunk[j][i].setLayout(new GridLayout(MAX_ROOT, MAX_ROOT, 5, 5));
            }
        }

        cell = new JComboBox[MAX][MAX];
        int val;
        for(int j = 0; j < MAX; j++){
            for(int i = 0; i < MAX; i++){
                cell[j][i] = new JComboBox();
                cell[j][i].setFont(font);
                cell[j][i].setMaximumRowCount(10);

                if(board.getOrig().get()[j][i] != 0){
                    val = board.puz[j][i];
                    cell[j][i].addItem(val);
                    cell[j][i].setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public void paint(Graphics g) {
                            setForeground(Color.RED);
                            super.paint(g);
                        }
                    });
                    cell[j][i].disable(); 
                }
                else{
                    cell[j][i].setForeground(Color.BLUE);
                    cell[j][i].insertItemAt("", 0);
                    for(int l = 1; l < 10; l++){
                        cell[j][i].addItem(l);
                    }
                }
                cell[j][i].addActionListener(this);

                chunk[j/3][i/3].add(cell[j][i]);
            }
        }
        
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                grid.add(chunk[j][i]);
            }
        }
        
        JPanel bottomP = new JPanel();
        bottomP.setLayout(new BoxLayout(bottomP, BoxLayout.Y_AXIS));
        
        JPanel loginP = new JPanel();
        userLabel = new JLabel("User:");
        loginP.add(userLabel);
        
        nameField = new JTextField(20);
        loginP.add(nameField);
        
        loginB = new JButton("Login");
        loginB.addActionListener(this);
        loginB.setToolTipText("Takes input username and creates ");
        loginP.add(loginB);
        
        points = new JLabel();
        loginP.add(points);
        
        JPanel buttonP = new JPanel();
        newGameB = new JButton("New Game");
        newGameB.addActionListener(this);
        newGameB.setToolTipText("Creates a new board to be solved");
        buttonP.add(newGameB);
        
        resetB = new JButton("Reset");
        resetB.addActionListener(this);
        resetB.setToolTipText("Resets board to first turn version");
        buttonP.add(resetB);
        
        submitB = new JButton("Submit");
        submitB.addActionListener(this);
        submitB.setToolTipText("Checks answers and saves to Sudoku.txt");
        buttonP.add(submitB);
        
        bottomP.add(loginP);
        bottomP.add(buttonP);
        
        add(rules, BorderLayout.PAGE_START);
        add(grid, BorderLayout.CENTER);
        add(bottomP, BorderLayout.PAGE_END);
    }
    
    /**
     * manages user interactions
     * @param e is the item being interacted with
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginB){
            name = nameField.getText();
            
            if(DBM.exists(name)){
                points.setText("Best: "+ DBM.getScore(name));
            }
        }
        else if(e.getSource() == newGameB){
            // this feels ineficient but it works
            GameSystem frame = new GameSystem("Sudoku");
            frame.pack();
            frame.setVisible(true);
            dispose();
        }
        else if(e.getSource() == resetB){
            for(int j = 0; j < MAX; j++){
                for(int i = 0; i < MAX; i++){
                    cell[j][i].setSelectedIndex(board.getOrig().get()[j][i]);
                    board.puz[j][i] = board.getOrig().get()[j][i];
                    board.resetTurn();
                    start = System.currentTimeMillis();
                }
            }
        }
        else if(e.getSource() == submitB){
            long elapsed = (System.currentTimeMillis() - start)/1000;
            new EndSequence(board, name, elapsed);
        }
        else{
            for(int j = 0; j < MAX; j++){
                for(int i = 0; i < MAX; i++){
                    if(e.getSource() == cell[j][i]){
                        board.puz[j][i] = cell[j][i].getSelectedIndex();
                        board.addTurn();
                    }
                }
            }
        }
    }
}
