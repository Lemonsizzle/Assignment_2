/**
 * saves a turn 1 version of the puzzle for clearing cells
 * this is needed so i can make turn 1 cells a different color
 * and so i can make turn 1 cells unchangeable
 * @author Zane (18040182)
 */
public class Original implements CommonVariables {
    private final int[][] ORIG;
    
    /**
     * constructor called after the removal process, before the first turn
     *      to make an array with the unchangeable cells
     * @param puz the puzzle array during the first turn
     */
    public Original(int[][] puz){
        ORIG = new int[MAX][MAX];
        
        for (int j = 0; j<MAX; j++){
            for (int i = 0; i<MAX; i++){
                ORIG[j][i] = puz[j][i];
            }
        }
        
    }
    
    /**
     * get method for the turn 1 puzzle
     * @return the puzzle during turn 1
     */
    public int[][] get(){
        return ORIG;
    }
}
