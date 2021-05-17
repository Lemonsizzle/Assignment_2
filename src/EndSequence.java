/**
 * this is the last class which:
 * prints board and incorrect cells
 * save orig, board, and ans to a txt file
 * @author Zane (18040182)
 */
public class EndSequence implements CommonVariables {
    
    /**
     * constructor for managing the final steps
     * @param board the board instance for accessing turns and board.toString
     * @param name of the user for the database
     * @param elapsed time taken to finish puzzle
     */
    public EndSequence(Board board, String name, long elapsed){
        System.out.println();
        
        board.getAns().check(board.puz);
        
        String topBar = "Your board is: (you took " + board.turns + " turns"
                + " and ";
        topBar += elapsed/3600 + "h:";
        topBar += ((elapsed%3600)/60) + "m:";
        topBar += ((elapsed%3600)%60) + "s)";
        System.out.println(topBar);
        System.out.println(board);
        
        new Save(board, topBar);
        
        System.out.println();
        
        System.out.println(board.getAns());
        
        if(!name.isEmpty()){
            DBM.setScore(name, board.getAns().getIncorrect());
        }
    }
}
