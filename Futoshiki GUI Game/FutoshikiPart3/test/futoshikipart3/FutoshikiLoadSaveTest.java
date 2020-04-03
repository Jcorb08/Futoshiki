/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipart3;

import futoshikipart2.Futoshiki;
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
public class FutoshikiLoadSaveTest {
    
    public FutoshikiLoadSaveTest() {
    
    }
    
    @Test
    public void saveandthenloadTest(){
        Futoshiki futo = new Futoshiki();
        futo.fillPuzzle(5, 5, 5);
        futo.setupSolvedPuzzle();
        assertTrue(futo.solve());
        System.out.println("save");
        System.out.println(futo.displayString());
        futo.saveGame();
        Futoshiki futo2 = new Futoshiki();
        futo2.fillPuzzle(5, 5, 5);
        futo2.setupSolvedPuzzle();
        assertTrue(futo2.solve());
        futo2.loadGame();
        System.out.println("load");
        System.out.println(futo2.displayString());
        assertEquals(futo.displayString(), futo2.displayString());
    }
   

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
