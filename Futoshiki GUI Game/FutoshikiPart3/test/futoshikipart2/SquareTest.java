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
 * @author Joe
 */
public class SquareTest {
    
        Futoshiki futo;
    
        static final String setSquareString = 
            "--- ---\n" +
            "|1| | |\n" +
            "--- ---\n" +
            "       \n" +
            "--- ---\n" +
            "| | | |\n" +
            "--- ---\n";

    @Test
    public void constructorTest(){
        FutoshikiSquare square = new FutoshikiSquare(true, 1,1,1);
        assertEquals(1, square.getValue());
        assertTrue(square.isEditable());
    }
    
    @Test
    public void setSquareTest(){
        futo = new Futoshiki(2);
        futo.setSquare(0, 0, 1);
        assertEquals(futo.displayString(), setSquareString);
    }
    
    @Test
    public void getSquareTest(){
        setSquareTest();
        assertEquals(1,futo.getSquare(0, 0));
    }
    
}
