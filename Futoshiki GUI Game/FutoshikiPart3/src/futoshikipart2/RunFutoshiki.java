/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipart2;

import java.util.Arrays;
import uk.ac.sussex.ianw.fp.futoshiki.text.Command;
import uk.ac.sussex.ianw.fp.futoshiki.text.CommandWord;
import uk.ac.sussex.ianw.fp.futoshiki.text.Parser;

/**
 *
 * @author Joe
 */
public class RunFutoshiki {
    
    private static Futoshiki puzzle;

    public Futoshiki getPuzzle() {
        return puzzle;
    }

    private static void runCommand(Command c){
        if (null == c.getCommand()) {
            System.out.println(c + " UNKNOWN COMMAND IN METHOD");
        }
        else switch (c.getCommand()) {
            case CLEAR:
                //get rid of contents in tile chosen
                if (puzzle.empty(c.getRow(), c.getColumn())){
                    System.out.println("Tile emptied at (" + c.getRow() + "," + 
                            c.getColumn() + ")");
                }
                else{
                    System.out.println("Clear command failed");
                }
                System.out.println(puzzle.displayString());
                break;
            case MARK:
                if(c.getValue() != 0){
                    puzzle.setSquare(c.getRow(), c.getColumn(), c.getValue());
                }
                if(puzzle.getSquare(c.getRow(), c.getColumn()) == 0){
                    System.out.println("Value not changed to  at (" + c.getRow() + 
                            "," + c.getColumn() + ")");
                }
                else{
                    System.out.println("Value changed to " + c.getValue() + " at (" + 
                            c.getRow() + "," + c.getColumn() + ")");
                }
                System.out.println(puzzle.displayString());
                break;
            case NEW:
                //creates new puzzle
                puzzle = new Futoshiki(c.getValue());
                puzzle.fillPuzzle(c.getValue(), c.getValue(), c.getValue());
                System.out.println("New Puzzle Created of Size " + c.getValue());
                System.out.println(puzzle.displayString());
                break;
            default:
                System.out.println(c + " UNKNOWN COMMAND IN METHOD");
                break;
        }
    }
    
    public static void main(String[] args){
        puzzle = new Futoshiki();
        puzzle.fillPuzzle(5, 5, 5);
        Parser p = new Parser();
        Command c = null;
        System.out.print("Enter a command>");
        while ((c = p.getCommand()) != null && c.getCommand() != CommandWord.QUIT) {
            System.out.println(c); 
            runCommand(c);
            System.out.println("Problems include: "+ Arrays.toString(puzzle.getProblems()));
            System.out.print(">");
        }
    }
    
}
