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
public abstract class Constraints {
    
    protected String value;
    protected int numberLeft;
    protected int numberRight;

    public String getValue() {
        return value;
    }

    public int getNumberLeft() {
        return numberLeft;
    }

    public int getNumberRight() {
        return numberRight;
    }
    
    /**
     * compares the values associated with the current constraint, 
     * and checks they are mathematically correct
     * @return true is correct, false otherwise
     * 
    */
    public boolean compareValues(){
        if(!(numberLeft == 0 && numberRight == 0)){
            if(value.equals("<")|| value.equals("^")){
                return numberLeft < numberRight;
            }
            else if(value.equals(">")|| value.equals("v")){
                return numberLeft > numberRight;
            }
        }
        return true;
    }
}
