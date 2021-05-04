import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * writes the initial board, the user's filled board, and the incorrect cells
 * all to a file
 * @author Zane (18040182)
 */
public class Save implements CommonVariables {
    
    /**
     * constructor for saving all the final printed parameters to a file
     * @param board the board needed for accessing puzzle and original arrays
     * @param topBar turns and time taken to finish
     */
    public Save(Board board, String topBar) {
        File save = new File("./Puzzle.txt");
        
        PrintWriter pw;
        try {
            if(save.createNewFile())
                System.out.println("Save Made");
            pw = new PrintWriter(new FileOutputStream(save), true);
            
            pw.println("Initial Board:");
            
            for(int j=0;j<MAX;j++){
                for(int i=0;i<MAX;i++){
                    if(i%3 == 2 && i != MAX-1){
                        if(board.getOrig().get()[j][i] == 0)
                            pw.print(" |");
                        else
                            pw.print(board.getOrig().get()[j][i] + "|");
                    }
                    else{
                        if(board.getOrig().get()[j][i] == 0)
                            pw.print(" ");
                        else
                            pw.print(board.getOrig().get()[j][i] + "");
                    }
                }
                if(j%3 == 2 && j != MAX-1)
                    pw.println("\n-----------");
                else
                    pw.println("");
            }
            
            pw.println();
            
            pw.println(topBar);
            
            for(int j=0;j<MAX;j++){
                for(int i=0;i<MAX;i++){
                    if(i%3 == 2 && i != MAX-1){
                        if(board.puz[j][i] == 0)
                            pw.print(" |");
                        else
                            pw.print(board.puz[j][i] + "|");
                    }
                    else{
                        if(board.puz[j][i] == 0)
                            pw.print(" ");
                        else
                            pw.print(board.puz[j][i] + "");
                    }
                }
                if(j%3 == 2 && j != MAX-1)
                    pw.println("\n-----------");
                else
                    pw.println("");
            }
            
            pw.println();
            
            pw.println(board.getAns());
            
            System.out.println("Puzzle Saved to File: " + save.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            System.err.println("why is this error happening");
        } catch (IOException ex) {
            System.err.println("i'm pretty sure you shouldn't see this");
        }
    }
    // this is the last class which prints the board to a txt file
}
