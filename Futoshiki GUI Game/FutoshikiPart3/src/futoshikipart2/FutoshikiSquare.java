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
public class FutoshikiSquare {
    
    private int value;
    private boolean editable;
    private final int columnValue;
    private final int rowValue;

    public int getColumnValue() {
        return columnValue;
    }

    public int getRowValue() {
        return rowValue;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getValue() {
        return value;
    }

    public boolean isEditable() {
        return editable;
    }
    
    public FutoshikiSquare(boolean editable, int value, int rowValue, int columnValue){
        this.editable = editable;
        this.value = value;
        this.columnValue = columnValue;
        this.rowValue = rowValue;
    }
}
