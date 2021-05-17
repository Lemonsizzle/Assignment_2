/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zane (18040182)
 */
public class Game implements CommonVariables {
    
    public static void main(String[] args) {
        GameSystem frame;
        do{
            frame = new GameSystem("Sudoku");
            frame.pack();
            frame.setVisible(true);
            while(!frame.getAgain()){
                
            }
        }while(frame.getAgain());
    }
}
