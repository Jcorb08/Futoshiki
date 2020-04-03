/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ConstraintsTest {
    
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
    
    static final String colRowPuzzleString = 
            "--- ---\n" +
            "| |>| |\n" +
            "--- ---\n" +
            " ^   v \n" +
            "--- ---\n" +
            "| |<| |\n" +
            "--- ---\n";
    

    @Test
    public void setInitialValuesTest(){
        Futoshiki futo = new Futoshiki();
        assertEquals(futo.displayString(),emptyPuzzleString);
    }
    
    @Test
    public void setValueColRowTest(){
        Futoshiki futo = new Futoshiki(2);
        futo.setColumnConstraint(0, 0, "<",futo.getColumnConstraints(),futo.getSquares());
        futo.setColumnConstraint(1, 0, ">",futo.getColumnConstraints(),futo.getSquares());
        futo.setRowConstraint(0, 0, ">",futo.getRowConstraints(),futo.getSquares());
        futo.setRowConstraint(1, 0, "<",futo.getRowConstraints(),futo.getSquares());
        futo.displayString();
        assertEquals(futo.displayString(),colRowPuzzleString);
    }

    @Test
    public void constructorTest(){
        Constraints con  = new GreaterThan();
        assertEquals(" ", con.value);
        con = new LessThan();
        assertEquals(" ", con.value);
        con = new GreaterThan(2, 1, 1);
        assertEquals("v", con.value);
        assertEquals(2, con.numberLeft);
        assertEquals(1, con.numberRight);
        con = new LessThan(1, 4, 0);
        assertEquals("<", con.value);
        assertEquals(1, con.numberLeft);
        assertEquals(4, con.numberRight);
    }
    
    @Test 
    public void compareConstraintsAndValuesTest(){
        Constraints con = new GreaterThan(3,1,1);
        assertTrue(con.compareValues());
        con = new GreaterThan(2,3,0);
        assertFalse(con.compareValues());
        con = new LessThan(1,3,1);
        assertTrue(con.compareValues());
        con = new LessThan(3,2,0);
        assertFalse(con.compareValues());
    }
}
