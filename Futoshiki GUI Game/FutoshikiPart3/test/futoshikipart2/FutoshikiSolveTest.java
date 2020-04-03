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
public class FutoshikiSolveTest {
    
    public FutoshikiSolveTest() {
    }
    
    @Test
    public void solveTest(){
        Futoshiki futo = new Futoshiki(5);
        futo.fillPuzzle(5, 5, 5);
        futo.setupSolvedPuzzle();
        assertTrue(futo.solve());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
