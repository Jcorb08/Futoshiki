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
public class IsLegalGetProblemsTests {
    
    Futoshiki filledPuzzle;
    
    public void setUp() {
        //sets up a Futoshiki puzzle
        // Note that we implicitly test the constraint setting here
        filledPuzzle = new Futoshiki();
        filledPuzzle.setColumnConstraint(0, 1, ">",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(4, 0, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(4, 2, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setColumnConstraint(4, 3, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setColumnConstraint(4, 2, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(3, 1, "<",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());
        filledPuzzle.setRowConstraint(1, 3, ">",filledPuzzle.getColumnConstraints(),filledPuzzle.getSquares());

        filledPuzzle.setSquare(4, 0, 4);
    }

    @Test
    public void isLegalLegalTest(){
        assertTrue(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalNumbersColTest(){
        filledPuzzle.setSquare(3, 0, 4);
        assertFalse(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalNumberRowTest(){
        filledPuzzle.setSquare(4, 4, 4);
        assertFalse(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalConstraintTest(){
        //cant test yet needs refactoring
        assertTrue(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalValuesTest(){
        //cant test yet needs refactoring
        assertTrue(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalValuesAndConstraintsColTest(){
        filledPuzzle.setSquare(1, 0, 3);
        filledPuzzle.setSquare(2, 0, 4);
        assertFalse(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void isLegalValuesAndConstraintsRowTest(){
        filledPuzzle.setSquare(4, 1, 2);
        assertFalse(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void getProblemsRowTest(){
        filledPuzzle.setSquare(4, 4, 4);
        String[] problems = new String[1];
        problems[0] = "Duplicate numbers in Row 4";
        assertArrayEquals(problems, filledPuzzle.getProblems());
    }
    
    @Test
    public void getProblemsColTest(){
        filledPuzzle.setSquare(3, 0, 4);
        String[] problems = new String[1];
        problems[0] = "Duplicate numbers in Column 0";
        assertArrayEquals(problems, filledPuzzle.getProblems());
    }
    
    @Test
    public void getProblemsColAndRowTest(){
        filledPuzzle.setSquare(3, 0, 4);
        filledPuzzle.setSquare(4, 4, 4);
        String[] problems = new String[2];
        problems[0] = "Duplicate numbers in Column 0";
        problems[1] = "Duplicate numbers in Row 4";
        assertArrayEquals(problems, filledPuzzle.getProblems());
    }
    
    @Test
    public void getProblemsConstraintsTest(){
        //test after refactor
        assertTrue(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void getProblemsValuesTest(){
        //test after refactor
        assertTrue(filledPuzzle.isLegal(filledPuzzle.getSquares(), filledPuzzle.getRowConstraints(), filledPuzzle.getColumnConstraints()));
    }
    
    @Test
    public void getProblemsConstraintsAndValuesTest(){
        filledPuzzle.setSquare(4, 1, 2);
        String[] problems = new String[1];
        problems[0] = "Incorrect logic after iteration 4";
        assertArrayEquals(problems, filledPuzzle.getProblems());
    }
}
