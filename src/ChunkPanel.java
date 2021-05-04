import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JComboBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zane (18040182)
 */
public class ChunkPanel extends JPanel implements ActionListener, CommonVariables {
    Board board;
    int offsetX, offsetY;
    JComboBox[][] cell;
    
    public ChunkPanel(Board board, int startX, int startY){
        setLayout(new GridLayout(MAX_ROOT, MAX_ROOT));
        cell = new JComboBox[MAX_ROOT][MAX_ROOT];
        
        offsetX = startX;
        offsetY = startY;
        int val;
        
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                cell[j][i] = new JComboBox();
                cell[j][i].setFont(font);
                cell[j][i].setMaximumRowCount(10);
                
                if(board.getOrig().get()[j+startY][i+startX] != 0){
                    val = board.getOrig().get()[j+startY][i+startX];
                    cell[j][i].addItem(val);
                    cell[j][i].disable();
                }
                else{
                    for(int l = 0; l < 10; l++){
                        cell[j][i].addItem(l);
                    }
                }
                cell[j][i].addActionListener(this);
                add(cell[j][i]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int j = 0; j < MAX_ROOT; j++){
            for(int i = 0; i < MAX_ROOT; i++){
                if(e.getSource() == cell[j][i]){
                    board.puz[j+offsetY][i+offsetX] = (int)cell[j][i].getSelectedItem();
                }
            }
        }
    }
}
