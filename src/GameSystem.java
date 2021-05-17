
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zane (18040182)
 */
public class GameSystem extends JFrame implements ActionListener, CommonVariables {
    // game panel
    Board board;
    JPanel[][] chunk;
    JComboBox[][] cell;
    
    // user panel
    JTextField nameField;
    JButton loginB;
    JLabel points;
    
    // button panel
    JButton newGameB, resetB, submitB;
    
    long start;
    String name = "";
    boolean logged;
    
    GameSystem(String title){
        super(title);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RulesPanel rules = new RulesPanel();
        
        start = System.currentTimeMillis();
        board = new Board();
        
        JPanel grid = new JPanel();
        setLayout(new GridLayout(MAX_ROOT, MAX_ROOT, 20, 20));
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
                    cell[j][i].disable();
                }
                else{
                    for(int l = 0; l < 10; l++){
                        cell[j][i].addItem(l);
                    }
                }
                cell[j][i].addActionListener(this);

                chunk[j/3][i/3].add(cell[j][i]);
            }
        }
        
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                add(chunk[j][i]);
            }
        }
        
        JPanel bottomP = new JPanel();
        bottomP.setLayout(new BoxLayout(bottomP, BoxLayout.Y_AXIS));
        
        JPanel loginP = new JPanel();
        nameField = new JTextField(20);
        loginP.add(nameField);
        
        loginB = new JButton("Login");
        loginB.addActionListener(this);
        loginP.add(loginB);
        
        points = new JLabel();
        loginP.add(points);
        
        JPanel buttonP = new JPanel();
        resetB = new JButton("Reset");
        resetB.addActionListener(this);
        buttonP.add(resetB);
        
        submitB = new JButton("Submit");
        submitB.addActionListener(this);
        buttonP.add(submitB);
        
        bottomP.add(loginP);
        bottomP.add(buttonP);
        
        add(rules, BorderLayout.PAGE_START);
        add(grid, BorderLayout.CENTER);
        add(bottomP, BorderLayout.PAGE_END);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginB){
            name = nameField.getText();
            logged = !nameField.getText().isEmpty();
            points.setText("Best: "+ DBM.getScore(name));
        }
        else if(e.getSource() == resetB){
            for(int j = 0; j < MAX; j++){
                for(int i = 0; i < MAX; i++){
                    cell[j][i].setSelectedItem(board.getOrig().get()[j][i]);
                    board.puz[j][i] = board.getOrig().get()[j][i];
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
                        board.puz[j][i] = (int)cell[j][i].getSelectedItem();
                        board.addTurn();
                        System.out.println("cell: "+(i+1)+" "+(j+1)+" changed");
                    }
                }
            }
        }
    }
    
}
