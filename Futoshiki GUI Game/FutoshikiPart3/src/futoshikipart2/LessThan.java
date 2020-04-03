/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipart2;

/**
 *
 * @author 198735
 */
public class LessThan extends Constraints {
    
    private String[] symbols;
    
    /**
     * Constructor when seating a value of a constraint
     * @param numberLeft
     * @param numberRight
     * @param symbol 
     */
    public LessThan(int numberLeft, int numberRight, int symbol){
        this.symbols = new String[] {"<","^"," "};
        value = symbols[symbol];
        this.numberLeft = numberLeft;
        this.numberRight = numberRight;
    }
    
    /**
     * Constructor when initialising constraint
     */
    public LessThan(){
        value = " ";
    }
    
    
}
