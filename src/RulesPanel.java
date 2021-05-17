
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * class for showing the rules for Sudoku and 
 * @author Zane (18040182)
 */
public class RulesPanel extends JPanel implements CommonVariables {
    /**
     * just for initializing
     */
    public RulesPanel(){
        int ruleCount = 5;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel header = new JLabel("<HTML><U><B>Sudoku Rules:</B></U></HTML>");
        JLabel[] rules = new JLabel[ruleCount];
        
        rules[0] = new JLabel("1. Only use the numbers 1 to 9");
        rules[1] = new JLabel("2. Avoid trying to guess the solution to the puzzle");
        rules[2] = new JLabel("3. Only use each number once in each row, column, & grid");
        rules[3] = new JLabel("4. Use the process of elimination as a tactic");
        rules[4] = new JLabel("5. If you wish to save your score then login at the bottom");
        
        header.setFont(font);
        add(header);
        for(int i = 0; i < rules.length; i++){
            rules[i].setFont(font);
            add(rules[i]);
        }
    }
}
