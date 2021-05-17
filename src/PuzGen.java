/**
 * class for creating the puzzle
 * @author Zane (18040182)
 */
public abstract class PuzGen implements CommonVariables {
    protected int[][] puz;
    protected int turns = 0;
    
    public PuzGen(){
        puz = new int[MAX][MAX]; // makes a 9x9 grid full of 0's
        fillAngle();
        fill(0, MAX_ROOT);
    }
    
    /**
     * fills the top left, centre, and bottom left boxes
     */
    public final void fillAngle(){
        for(int i = 0; i<MAX; i=i+MAX_ROOT)
            fillBox(i,i);
    }
    
    /**
     * checks a random value against the values in a box to
     *      see if that value already exists in the box
     * @param y the top most row of the box
     * @param x the left most col of the box
     */
    public void fillBox(int y, int x){
        int val;
        for(int j = 0; j<MAX_ROOT; j++){
            for(int i = 0; i<MAX_ROOT; i++){
                do{
                    val = (int) (Math.random()*MAX+1);
                }while(!boxCheck(y, x, val));
                
                puz[y+j][x+i] = val;
            }
        }
    }
    
    /**
     * recursive method for filling remaining puzzle array
     * @param j the y axis to be filled
     * @param i the x axis to be filled
     * @return 
     */
    public final boolean fill(int j, int i){
        if(i>=MAX && j<MAX-1){
            j = j+1;
            i = 0;
        }
        
        if(j>=MAX && i>=MAX)
            return true;
        
        if(j<MAX_ROOT){
            if(i<MAX_ROOT)
                i = MAX_ROOT;
        }
        else if(j < MAX-MAX_ROOT){
            if(i==(int)(j/MAX_ROOT)*MAX_ROOT)
                i = i + MAX_ROOT;
        }
        else{
            if(i == MAX-MAX_ROOT){
                j = j + 1;
                i = 0;
                if(j>=MAX)
                    return true;
            }
        }
        
        for(int val = 1; val<=MAX; val++){
            if(bigCheck(j, i, val)){
                puz[j][i] = val;
                if(fill(j, i+1))
                    return true;
                
                puz[j][i] = 0;
            }
        }
        return false;
    }
    
    /**
     * a method used for checking the row, column, and box of a given cell
     * @param j the y axis of the cells being checked
     * @param i the x axis of the cells being checked
     * @param val the value being checked if it exists
     * @return if the chosen cell can be val
     */
    public boolean bigCheck(int j, int i, int val){
        return(rowCheck(j, val) &&
                colCheck(i, val) &&
                boxCheck(j-j%MAX_ROOT, i-i%MAX_ROOT, val));
    }
    
    /**
     * a method for checking a row to see if a value exists
     * @param j the y axis of the row being checked
     * @param val the value being checked
     * @return if the chosen value is already in the row
     */
    public boolean rowCheck(int j, int val){
        for(int i = 0; i<MAX; i++)
            if(puz[j][i] == val)
                return false;
        return true;
    }
    
    /**
     * a method for checking a col to see if a value exists
     * @param i the x axis of the col being checked
     * @param val the value being checked
     * @return if the chosen value is already in the col
     */
    public boolean colCheck(int i, int val){
        for(int j = 0; j<MAX; j++)
            if(puz[j][i] == val)
                return false;
        return true;
    }
    
    /**
     * a method for checking a box to see if a value exists
     * @param startY the top most row of the box
     * @param startX the left most col of the box
     * @param val the value being checked
     * @return if the chosen value is already in the box
     */
    public boolean boxCheck(int startY, int startX, int val){
        for(int j = 0; j<MAX_ROOT; j++){
            for(int i = 0; i<MAX_ROOT; i++){
                if(puz[startY+j][startX+i] == val)
                    return false;
            }
        }
        return true;
    }
}
