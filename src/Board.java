/**
 * class containing the puzzle's:
 *      final answer
 *      cell clearing
 *      and turn 1 version
 * @author Zane (18040182)
 */
public class Board extends PuzGen implements CommonVariables {
    private final Answer ans;
    private final Original orig;
    
    /**
     * constructor for generating the puzzle
     */
    public Board(){
        super();
        
        ans = new Answer(puz);
        
        // sweeps cells
        for(int yS = 0; yS < MAX; yS++){
            for(int xS = 0; xS < MAX; xS++)
                sweep(yS,xS);
        }
        
        orig = new Original(puz);
    }
    
    /**
     * removes random cells from the filled puzzle to make it a unique sudoku
     * @param yS the y axis of the value being maybe converted to 0
     * @param xS the x axis of the value being maybe converted to 0
     */
    private void sweep(int yS, int xS){
        int yI, yF; // top and bottom most row of box
        int xI, xF; // left and right most col of box
        int b, c;   // x and y position
        int rem1, rem2; // y and x postition in box
        boolean flag;
        int count = MAX;
        for(int j = 1; j<=MAX; j++){
            flag=true;
            for(int i = 0; i<MAX; i++){
                if(i != xS){
                    if(j == puz[yS][i]){
                        flag=false;
                        break;
                    }
                }
            }
            
            if(flag==true){
                for(c = 0; c<MAX; c++){
                    if(c!=yS){
                        if(j == puz[c][xS]){
                            flag=false;
                            break;
                        }
                    }
                }
            }
            
            if(flag==true){
                rem1=yS%3;
                rem2=xS%3;
                yI=yS-rem1;
                yF=yS+(2-rem1);
                xI=xS-rem2;
                xF=xS+(2-rem2);
                for(c = yI; c<=yF; c++){
                    for(b = xI; b<=xF; b++){
                        if((c != yS) && (b != xS)){
                            if(j == puz[c][b]){
                                flag=false;
                                break;
                            }
                        }
                    }
                }
            }
            
            if(flag == false)
                count--;
        }
        if(count == 1){
            puz[yS][xS]=0;
        }
    }
    
    /**
     * get method for the turn 1 version of the puzzle
     * @return the puzzle at turn 1
     */
    public Original getOrig(){
        return orig;
    }
    
    /**
     * get method for the answer of the puzzle
     * @return the puzzle's answer
     */
    public Answer getAns() {
        return ans;
    }
    
    /**
     * increments turns by 1
     * called when a cell changes
     */
    public void addTurn(){
        turns++;
    }
    
    /**
     * creates a string that contains the puzzle
     *      in a user friendly way for inputs
     * @return the string
     */
    @Override
    public String toString(){
        String str = "";
        
        for(int j = 0; j<MAX; j++){
            for(int i = 0; i<MAX; i++){
                if(i%3 == 2 && i != MAX-1){
                    if(puz[j][i] == 0)
                        str += (" |");
                    else if(orig.get()[j][i] != 0)
                        str += (DEF_NUM + puz[j][i] + NORMAL + "|");
                    else
                        str += (puz[j][i] + "|");
                }
                else if(puz[j][i] == 0)
                    str += ("  ");
                else if(orig.get()[j][i] != 0)
                    str += (DEF_NUM + puz[j][i] + NORMAL + " ");
                else
                    str += (puz[j][i] + " ");
            }
            if(j%3 == 2 && j != MAX-1)
                str += ("\n------------------\n");
            else
                str += ("\n");
        }
        return str;
    }
}
