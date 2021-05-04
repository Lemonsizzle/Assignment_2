
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    Board board;
    JComboBox[][] cell;
    JButton resetB;
    JButton submitB;
    long start;
    
    GameSystem(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start = System.currentTimeMillis();
        board = new Board();
        
        RulesPanel rules = new RulesPanel();
        
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(MAX_ROOT, MAX_ROOT, 20, 20));
        JPanel[][] chunk;
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
        
        JPanel buttonP = new JPanel();
        resetB = new JButton("Reset");
        resetB.addActionListener(this);
        buttonP.add(resetB);
        
        submitB = new JButton("Submit");
        submitB.addActionListener(this);
        buttonP.add(submitB);
        
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                grid.add(chunk[j][i]);
            }
        }
        
        setSize(500, 600);
        add(rules, BorderLayout.PAGE_START);
        add(grid, BorderLayout.CENTER);
        add(buttonP, BorderLayout.PAGE_END);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resetB){
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
            new EndSequence(board, elapsed);
        }
        else{
            for(int j = 0; j < MAX; j++){
                for(int i = 0; i < MAX; i++){
                    if(e.getSource() == cell[j][i]){
                        board.puz[j][i] = (int)cell[j][i].getSelectedItem();
                    }
                }
            }
        }
    }
    
}
