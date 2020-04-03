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
public class GreaterThan extends Constraints {
    
    private String[] symbols;

    /**
     * Constructor when setting a value of constraint
     * @param numberLeft
     * @param numberRight
     * @param symbol 
     */
    public GreaterThan(int numberLeft, int numberRight, int symbol){
        this.symbols = new String[] {">","v"," "};
        value = symbols[symbol];
        this.numberLeft = numberLeft;
        this.numberRight = numberRight;
    }
    
    /**
     * Constructor when initialising constraints
     */
    public GreaterThan(){
        value = " ";
    }

}
