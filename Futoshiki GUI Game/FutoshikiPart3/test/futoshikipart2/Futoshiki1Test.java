/**
 * Simple Futoshiki tests for part 1
 */
package futoshikipart2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 198735
 */
public class Futoshiki1Test {

    Futoshiki filledPuzzle;

    static final String emptyPuzzleString = 
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                   \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                   \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                   \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                   \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n";

    static final String filledPuzzleString = 
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                   \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | |>| |\n" +
            "--- --- --- --- ---\n" +
            " v                 \n" +
            "--- --- --- --- ---\n" +
            "| | | | | | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                 ^ \n" +
            "--- --- --- --- ---\n" +
            "| | | |<| | | | | |\n" +
            "--- --- --- --- ---\n" +
            "                 ^ \n" +
            "--- --- --- --- ---\n" +
            "|4|<| | | |<| | | |\n" +
            "--- --- --- --- ---\n";


    @Before
    public void setUp() {
        //sets up a Futoshiki puzzle
        // Note that we implicitly test the constraint setting here
        filledPuzzle = new Futoshiki(5);
        
        filledPuzzle.setColumnConstraint(0, 1, ">",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(4, 0, "<",filledPuzzle.getRowConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(4, 2, "<",filledPuzzle.getRowConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setColumnConstraint(4, 3, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setColumnConstraint(4, 2, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(3, 1, "<",filledPuzzle.getRowConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(1, 3, ">",filledPuzzle.getRowConstraints(),filledPuzzle.getSquares());

        filledPuzzle.setSquare(4, 0, 4);
    }

    public void tearDown() {
    }

    @Test
    public void settingTest() {
        Futoshiki fs = new Futoshiki(6);
        fs.setSquare(0, 0, 1);
        assertEquals(1,fs.getSquare(0,0));
        fs.setSquare(0, 0, -1);
        assertEquals(1,fs.getSquare(0,0));
        // Note we check different coordinates
        fs.setSquare(5,5,4);
        assertEquals(4,fs.getSquare(5,5));
        fs.setSquare(5,5,7);
        assertEquals(4,fs.getSquare(5,5));
        fs.setSquare(6,6,3);
        assertEquals(0,fs.getSquare(6,6));
        
    }
    
    @Test 
    public void displayStringSmallTest() {
        Futoshiki fs = new Futoshiki(1);
        assertEquals("---\n| |\n---\n",fs.displayString());
    }
    @Test
    public void displayStringTest() {

        Futoshiki fs = new Futoshiki();
        assertEquals(emptyPuzzleString, fs.displayString());

    }

    @Test
    public void printFilledTest() {
        assertEquals(filledPuzzleString, filledPuzzle.displayString());
    }
    
    @Test
    public void isLegalFilledPuzzleTest(){
        Futoshiki futo = new Futoshiki();
        futo.fillPuzzle(5, 5, 5);
        assertTrue(futo.isLegal(futo.getSquares(), futo.getRowConstraints(), futo.getColumnConstraints()));
    }
    
    @Test
    public void setSquareStillEditiableTest(){
        assertEquals(4,filledPuzzle.getSquare(4, 0));
        filledPuzzle.setSquare(4, 0, 2);
        assertEquals(2, filledPuzzle.getSquare(4, 0));
    }
    
    @Test
    public void emptyTest(){
        assertTrue(filledPuzzle.empty(4,0));
        assertEquals(0,filledPuzzle.getSquare(4, 0));
    }
    
    @Test
    public void isPuzzleCompleteTest(){
        Futoshiki futo = new Futoshiki(2);
        futo.setSquare(0, 0, 1);
        futo.setSquare(0, 1, 2);
        futo.setSquare(1, 0, 2);
        futo.setSquare(1, 1, 1);
        futo.setColumnConstraint(0, 0, "<",futo.getColumnConstraints(),futo.getSquares());
        assertTrue(futo.isPuzzleComplete());
    }
    
   @Test
   public void testRandomPuzzle() {
//       // This is a one off test.  One might argue that this is a random 
//        // method, so should be called multiple times.  However, given the
//        // fill occupancy used, there are multiple collisions with very
//        // high probability
    Futoshiki fs = new Futoshiki(5);
    fs.fillPuzzle(5,5,5);

    String s = fs.displayString();
    int[] counter = new int[3];
    for (int i = 0; i < s.length(); i++) {
        switch(s.charAt(i)) {
            case '1': case'2': case '3': case '4': case'5':  {
                counter[0]++;
                break;
            }
            case '<': case '>': {
                counter[1]++;
                break;
            }
            case 'v': case '^': {
                counter[2]++;
                  break;
            }
        }
    }
    for(int count: counter) {
        assertEquals(5, count);
    }
   }
}
