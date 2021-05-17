/**
 * saves the answer for the puzzle
 * checks the answer
 * prints the incorrect cells
 * @author Zane (18040182)
 */
public class Answer implements CommonVariables {
    private final int[][] ANS;
    private final boolean[][] RESULTS;
    private int incorrect = 0;
    
    /**
     * initializes the final answer for the puzzle
     * @param puz the puzzle which is having its answer saved
     */
    public Answer(int[][] puz){
        ANS = new int[MAX][MAX];
        RESULTS = new boolean[MAX][MAX];
        
        for (int j = 0; j<MAX; j++){
            for (int i = 0; i<MAX; i++)
                ANS[j][i] = puz[j][i];
        }
    }
    
    /**
     * 
     * @return number of incorrect values
     */
    public int getIncorrect(){
        return incorrect;
    }
    
    /**
     * get method for the answer to the puzzle
     * @return the answer array for the puzzle
     */
    public int[][] get(){
        return ANS;
    }
    
    /**
     * checks the puzzle to see if any cells are incorrect
     * @param puz the puzzle to be checked
     */
    public void check(int[][] puz){
        incorrect = 0;
        for(int j = 0; j < MAX; j++){
            for(int i = 0; i < MAX; i++){
                RESULTS[j][i] = puz[j][i] == ANS[j][i];
                if(!RESULTS[j][i])
                    incorrect++;
            }
        }
    }
    
    /**
     * creates a string the at contains all incorrect cells and their proper value
     * @return the string mentioned above
     */
    @Override
    public String toString(){
        String str = "Incorrect Cells:\n";
        str += "XY = Answer\n";
        str += "___________\n";
        String x, y;
        for(int j = 0; j < RESULTS.length; j++){
            for(int i = 0; i < RESULTS.length; i++){
                if(!RESULTS[j][i]){
                    x = String.valueOf((char)(i+65));
                    y = String.valueOf((char)(j+65));
                    str += x + y + " = " + ANS[j][i] + "\n";
                }
            }
        }
        if(incorrect == 0){
            str = "You got all cells correct";
            return str;
        }
        else{
            str += "You got " + incorrect + " cells wrong";
            return str;
        }
    }
}
