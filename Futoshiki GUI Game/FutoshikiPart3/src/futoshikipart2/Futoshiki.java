package futoshikipart2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * This class is the "model answer" to the first part
 * of the Futoshiki assignment. There will be other equally good
 * (or even better) solutions, as well as lots of worse ones!
 * 
 * @author 198735
 * @version 1.0
 */
public class Futoshiki
{
    /****************************************************************
     * constant to specify DEFAULT size of puzzle
     ****************************************************************/
    public static final int DEFAULT_GRIDSIZE = 5;
    private int gridsize;
    private FutoshikiSquare[][] squares;
    private FutoshikiSquare[][] latinSquare;
    private FutoshikiSquare[][] solvedPuzzle;
    private Constraints[][] rowConstraints;
    private Constraints[][] columnConstraints;

    public static int getDEFAULT_GRIDSIZE() {
        return DEFAULT_GRIDSIZE;
    }

    public int getGridsize() {
        return gridsize;
    }

    public FutoshikiSquare[][] getSquares() {
        return squares;
    }
    
    public FutoshikiSquare[][] getSolvedPuzzle(){
        return latinSquare;
    }

    public Constraints[][] getRowConstraints() {
        return rowConstraints;
    }

    public Constraints[][] getColumnConstraints() {
        return columnConstraints;
    }

    
    /****************************************************************
     * Creates a completely blank puzzle of default size
     * @param gridsize
     ****************************************************************/
    public Futoshiki(int gridsize)
    {
        this.gridsize = gridsize;
        squares = new FutoshikiSquare[gridsize][gridsize];
        rowConstraints = new Constraints[gridsize][gridsize - 1];
        columnConstraints = new Constraints[gridsize][gridsize - 1];
        setup();
        
    }
    
    private void setup(){
        
        for (int row = 0; row < gridsize; row++){
            for (int column = 0; column < gridsize; column++){
                squares[row][column] = new FutoshikiSquare(true, 0, row, column);
            }
        }
        
        //set up initial row constraints (no constraints)
        for (int row = 0; row < gridsize; row++) {
            for (int column = 0; column < gridsize - 1; column++) {
                rowConstraints[row][column] = new GreaterThan();
            }
        }
        
        //set up initial column constraints (no constraints)
        for (int column = 0; column < gridsize; column++) {
            for (int row = 0; row < gridsize - 1; row++) {
                columnConstraints[column][row] = new GreaterThan();
            }
        }
        this.latinSquare = new FutoshikiSquare[gridsize][gridsize];
    }
    
    public Futoshiki() {
        this(DEFAULT_GRIDSIZE);
    }
    
    // Convenience check method
    private boolean checkCoordinates(int row, int column) {
        return row >= 0 && row < gridsize && column >= 0 && column < gridsize;
    }
    
    private boolean checkColCoordinates(int row, int col){
        return row >= 0 && row < gridsize-1 && col >= 0 && col < gridsize;
    }
    
    private  boolean checkRowCoordinates(int row, int col){
        return row >= 0 && row < gridsize && col >= 0 && col < gridsize-1;
    }
    
    /*************************************************************
     * sets cell at specified row and column to the value specified
     * @param row the row number
     * @param column the column number
     * @param val the value to be stored
     *************************************************************/
    public void setSquare(int row, int column, int val)
    {
        
        if (val >= 0 && val <= gridsize && checkCoordinates(row,column) && squares[row][column].isEditable()) {
            squares[row][column] = new FutoshikiSquare(true,val,row,column);
        }
    }

    
    public int getSquare(int row, int column) {
        if(checkCoordinates(row,column)) {
            return squares[row][column].getValue();
        }
        return 0;
    }
    
        /*************************************************************
     * sets a constraint in a specified row between the cell at the
     * specified column and the next one
     * @param row the row number
     * @param col the column number
     * @param relation the constraint (">" or "<")
     * @param rowTiles
     * @param tiles
     *************************************************************/
      public void setRowConstraint(int row, int col, String relation, Constraints[][] rowTiles, FutoshikiSquare[][] tiles)
    {
        if (relation.equals("<")) {
            rowTiles[row][col] = new LessThan(
                    tiles[row][col].getValue(),
                    tiles[row][col+1].getValue(),0);
        }
        else if (relation.equals(">")){
            rowTiles[row][col] = new GreaterThan(
                    tiles[row][col].getValue(),
                    tiles[row][col+1].getValue(),0);
        }
        else if (relation.equals(" ")){
            rowTiles[row][col] = new GreaterThan(
                    tiles[row][col].getValue(),
                    tiles[row][col+1].getValue(),2);            
        }
    }
    
    /*************************************************************
     * sets a constraint in a specified column between the cell at the
     * specified row and the next one
     * @param col the column number
     * @param row the row number
     * @param relation the constraint ("<" or ">")
     * @param colTiles
     * @param tiles
     *************************************************************/
    public void setColumnConstraint(int col, int row, String relation, Constraints[][] colTiles, FutoshikiSquare[][] tiles)
    {
        if (relation.equals("<")) {
            colTiles[col][row] = new LessThan(
                    tiles[row][col].getValue(),
                    tiles[row+1][col].getValue(),1);
        }
        else if (relation.equals(">")){
            colTiles[col][row] = new GreaterThan(
                    tiles[row][col].getValue(),
                    tiles[row+1][col].getValue(),1);
        }
        else if (relation.equals(" ")){
            colTiles[col][row] = new GreaterThan(
                    tiles[row][col].getValue(),
                    tiles[row+1][col].getValue(),2);            
        }
    }
    
    /**
     * Sets the tile to its original form i.e 0
     * @param row
     * @param col
     * @return true if success, false otherwise
     */
    public boolean empty(int row, int col){
        if (squares[row][col].isEditable()){
            squares[row][col].setValue(0);
            return true;
        }
        return false;
    }
   
    /**
     * checks if puzzle is complete depending on whether its legal and is full
     * @return true if complete, false otherwise
     */
    public boolean isPuzzleComplete(){
        if(isLegal(squares,rowConstraints,columnConstraints)){
            for(int i = 0; i < gridsize; i++){
                for(int j = 0; j < gridsize; j++){
                    if(squares[i][j].getValue() == 0){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean compareNumbers(int[] numbers){
        for(int i = 0; i < gridsize-1; i++){
            if((numbers[i] == numbers[i+1]) && (numbers[i] != 0)){
                return false;
            }
        }
        return true;
    }
    
    private boolean compareCol(int col, FutoshikiSquare[][] tiles){
        int[] numbers  = new int[gridsize];
        for(int i = 0; i < gridsize; i++){
            numbers[i] = tiles[i][col].getValue();
        }
        Arrays.sort(numbers);
        return compareNumbers(numbers);
    }
    
    private boolean compareRow(int row, FutoshikiSquare[][] tiles){
        int[] numbers  = new int[gridsize];
        for(int i = 0; i < gridsize; i++){
            numbers[i] = tiles[row][i].getValue();
        }
        Arrays.sort(numbers);
        return compareNumbers(numbers);
    }
    
    public boolean compareValues(Constraints tile){
        if(!(tile.numberLeft == 0 && tile.numberRight == 0)){
            if(tile.value.equals("<")|| tile.value.equals("^")){
                return tile.numberLeft < tile.numberRight;
            }
            else if(tile.value.equals(">")|| tile.value.equals("v")){
                return tile.numberLeft > tile.numberRight;
            }
        }
        else if(tile.value.equals("<") || tile.value.equals("^")){
            if(tile.numberLeft == gridsize || tile.numberRight == 1){
                return false;
            }
        }
        else if(tile.value.equals(">") || tile.value.equals("v")){
            if(tile.numberLeft == 1 || tile.numberRight == gridsize){
                return false;
            }
        }
        return true;
    }
    
    public boolean compareNumbersAndConstraint(int i, Constraints[][] rowTiles, Constraints[][] colTiles){
        for(int j = 0; j < gridsize-1; j++){
            if(!compareValues(rowTiles[i][j])){
                return false;
            }
            if(!compareValues(colTiles[i][j])){
                return false;
            }
        }
        return true;
    }
    
    public boolean compareConstraint(int i, Constraints[][] rowTiles, Constraints[][] colTiles) {
        for(int j = 0; j < gridsize-1; j++){
            //System.out.println("Row: " + i + "Col: " + j);
            if (!((rowTiles[i][j].value.equals(">"))||
                (rowTiles[i][j].value.equals("<"))||
                (rowTiles[i][j].value.equals(" ")))){
                    return false;
            }
            //System.out.println("Row: " + j + "Col: " + i);
            if (!((colTiles[i][j].value.equals("^"))||
                (colTiles[i][j].value.equals("v"))||
                (colTiles[i][j].value.equals(" ")))){    
                    return false;
            }
        }
        return true;
    }
    
    private boolean compareValues(int i,FutoshikiSquare[][] tiles){
        for(int j =0; j < gridsize; j++){
            if (!((tiles[i][j].getValue() >= 0)&&(tiles[i][j].getValue() <= gridsize))){
                    return false;
            }
       
        }
        return true;
    }
    
    /**
     * checks if the puzzle is legal, uses checks such as out of bounds, 
     * duplicate in row/column and whether the constraints work mathematically
     * @param tiles
     * @param rowTiles
     * @param colTiles
     * @return true if legal, false otherwise
     */
    public boolean isLegal(FutoshikiSquare[][] tiles, Constraints[][] rowTiles, Constraints[][] colTiles){
        boolean legal = false;
        for(int i = 0; i < gridsize; i++){
            legal = compareCol(i,tiles);
            if (!legal){return legal;}
            //System.out.println("compareCol");
            legal = compareRow(i,tiles);
            if(!legal){return legal;}
            //System.out.println("compareRow");
            legal = compareConstraint(i,rowTiles,colTiles);
            if(!legal){return legal;}
            //System.out.println("compareConstraint");
            legal = compareValues(i,tiles);
            if(!legal){return legal;}
            //System.out.println("compareValues");
            legal = compareNumbersAndConstraint(i,rowTiles,colTiles);
            if(!legal){return legal;}
            //System.out.println("compareNum+Con");
        }
        return legal;
    }
    
    /**
     * works out the list of problems if the puzzle is not legal
     * @return String array of problems
     */
    public String[] getProblems(){
        if(!isLegal(squares,rowConstraints,columnConstraints)){
            String row = "Duplicate numbers in Row ";
            String col = "Duplicate numbers in Column ";
            String constraint = "Incorrect constraint after iteration ";
            String value = "Incorrect number in Row ";
            String valueConstraint = "Incorrect logic after iteration ";
            int problemsLength = 0;
            String[] problems = new String[0];
            boolean legal;
            for(int i = 0; i < gridsize; i++){
                legal = compareCol(i,squares);
                if (!legal){
                    problemsLength += 1;
                    problems = Arrays.copyOf(problems, problemsLength);
                    problems[problemsLength-1] = col + i;
                    System.out.println(problems[problemsLength-1]);
                }
                legal = compareRow(i,squares);
                if(!legal){
                    problemsLength += 1;
                    problems = Arrays.copyOf(problems, problemsLength);
                    problems[problemsLength-1] = row + i;
                    System.out.println(problems[problemsLength-1]);
                }
                legal = compareConstraint(i,rowConstraints,columnConstraints);
                if(!legal){
                    problemsLength += 1;
                    problems = Arrays.copyOf(problems, problemsLength);
                    problems[problemsLength-1] = constraint + i;
                    System.out.println(problems[problemsLength-1]);
                }
                legal = compareValues(i,squares);
                if(!legal){
                    problemsLength += 1;
                    problems = Arrays.copyOf(problems, problemsLength);
                    problems[problemsLength-1] = value + i;
                    System.out.println(problems[problemsLength-1]);
                }
                legal = compareNumbersAndConstraint(i,rowConstraints,columnConstraints);
                if(!legal){
                    problemsLength += 1;
                    problems = Arrays.copyOf(problems, problemsLength);
                    problems[problemsLength-1] = valueConstraint + i;
                    System.out.println(problems[problemsLength-1]);
                }
            }
            return problems;
        }else{
            return null;
        }
    }
    
    
    /********************************************************************
     * Fills a Futoshiki puzzle with the randomized values and constraints.This is not guaranteed to be legal
     * @param numValues How many values to enter
     * @param numHorizontal How many horizontal constraints
     * @param numVertical How many vertical constraints 
     ********************************************************************/
    public void fillPuzzle(int numValues, int numHorizontal, int numVertical) {

        // There is repetition of code here, but removing the repetition
        // requires some functional manipulation which we haven't covered yet 
            int[][] latin = getLatinSquare();
            int countHorizontal = 0;
            while(countHorizontal < numHorizontal) {
                Random rand = new Random();
                int x = rand.nextInt(gridsize);
                int y = rand.nextInt(gridsize-1);
                if(rowConstraints[x][y].value.equals(" ")) {
                    String s = randomConstraint();
                    if(s.equals("<") && (latin[x][y] < latin[x][y+1])){
                        setRowConstraint(x, y, s,rowConstraints,squares);
                        countHorizontal++;
                    }
                    else if(s.equals(">") && (latin[x][y] > latin[x][y+1])){
                        setRowConstraint(x, y, s,rowConstraints,squares);
                        countHorizontal++;
                    }
                    else{
                        
                    }
                }
            }
            int countVertical = 0;
            while(countVertical < numVertical) {
                Random rand = new Random();
                int x = rand.nextInt(gridsize);
                int y = rand.nextInt(gridsize-1);
                if(columnConstraints[x][y].value.equals(" ")) {
                    String s = randomConstraint();
                    if(s.equals("<") && (latin[y][x] < latin[y+1][x])){
                        setColumnConstraint(x, y, s,columnConstraints,squares);
                        countVertical++;
                    }
                    else if(s.equals(">") && (latin[y][x] > latin[y+1][x])){
                        setColumnConstraint(x, y, s, columnConstraints, squares);
                        countVertical++;
                    }
                    else{
                    
                    }
                }
            }
            int countValues = 0;
            while(countValues < numValues) {
                Random rand = new Random();
                int x = rand.nextInt(gridsize);
                int y = rand.nextInt(gridsize);
                if(squares[x][y].getValue() == 0) {
                    squares[x][y] = new FutoshikiSquare(false, latin[x][y],x,y);
                    countValues++;
                }
            }
            System.out.println(displayString());
            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    this.latinSquare[i][j] = new FutoshikiSquare(true, latin[i][j], i, j);
                    //System.out.print(this.latinSquare[i][j].getValue() + " ");
                }
                //System.out.println("\n");
            }
    }
    
    
    private int[][] getLatinSquare()
    {
        //row col
        int[][] latinSquare = new int[gridsize][gridsize];
        int[] selectingList = new int[gridsize];
        //two while
        int j = 0;
        while (j < gridsize){
            selectingList = shuffleArray(selectingList);
            for (int k = 0; k < gridsize; k++) {
                latinSquare[k][j] = selectingList[k];
            }
            boolean legal = true;
            for (int i = 0; i < gridsize; i++) {
                legal = compareLatinRow(i, latinSquare);
                if (!legal){
                    break;
                }
            }
            if(legal){            
                j++;
            }
        }
        
        return latinSquare;
    }
    
    private int[] shuffleArray(int[] selectingList){
        
        for (int i = 1; i <= gridsize; i++) {
            selectingList[i-1] = i;
        }
        
        Random rand = new Random();
        for (int k = gridsize-1; k > 0; k--) {
            int index = rand.nextInt(k+1);
            int swap = selectingList[index];
            selectingList[index] = selectingList[k];
            selectingList[k] = swap;
        }
        
        return selectingList;
    }
    
    private boolean compareLatinRow(int row,int[][] latin){
        int[] numbers  = new int[gridsize];
        for(int i = 0; i < gridsize; i++){
            numbers[i] = latin[row][i];
        }
        Arrays.sort(numbers);
        return compareNumbers(numbers);
    }
    
    private String randomConstraint()
    {
        Random rand = new Random();
        int i = rand.nextInt();
        if(i%2 == 0){
            return "<";
        }
        return ">";
    }
    
    /********************************************************************
     * Returns a String in Ascii for pretty printing
     * @return The string representing the puzzle
     ********************************************************************/
    public String displayString()
    {
        String s = "";
        for (int row = 0; row < gridsize - 1; row++) {
            s += drawRow(row);
            s += drawColumnConstraints(row);
        }
        s += drawRow(gridsize - 1);
        return s;
    }
    
    private String printTopBottom()
    {
        String s = "";
        for (int col = 0; col < gridsize; col++) {
            s += "---";
            if(col < (gridsize-1)) {
                s += " ";
            }
        }
        return s + "\n";
    }
    
    private String drawColumnConstraints(int row) 
    {
        String s = " ";
        for (int col = 0; col < gridsize; col++) {
            s += columnConstraints[col][row].value + " ";
            if(col < (gridsize-1)) {
                s += "  ";
            }
        }
        return s + "\n";
    }
    
    private String drawRow(int row) 
    {
        String s = printTopBottom();
        for (int col = 0; col < gridsize; col++) {
            if (squares[row][col].getValue() > 0) {
                s += "|" + squares[row][col].getValue() + "|";
            }
            else {
                s += "| |";
            }
            if (col < gridsize - 1) {
                s += rowConstraints[row][col].value;
            }
        }
        return s + "\n" + printTopBottom();
    }

    public static void main(String[] args) {
        Futoshiki f = new Futoshiki(6);
        f.fillPuzzle(10, 10, 10);
        System.out.println("Futoshiki with 10 values, row and vertical constraints");
        System.out.print(f.displayString());
    }
    
    public boolean solve(){
            
        if(!isLegal(solvedPuzzle, rowConstraints, columnConstraints)){
            return false;
        }
        else if (isPuzzleComplete()){
            return true;
        }
        else {
            Stack s = new Stack();
            Stack discovered = new Stack();
            int col = 0;
            int row = 0;
            s.push(solvedPuzzle[row][col]);
            while(!s.isEmpty()){
                FutoshikiSquare v = (FutoshikiSquare) s.pop();
                if(discovered.search(v) == -1){
                    discovered.push(v);
                    row = v.getRowValue();
                    col = v.getColumnValue();
                    //System.out.println("Solve() " + row + " " + col);

                    if(v.isEditable() && v.getValue() == 0){
                        int i = 1;
                        while (i <= gridsize){
                            solvedPuzzle[row][col].setValue(i);
                            if(solve()){
                                //System.out.println(true);
                                return true;
                            }
                            solvedPuzzle[row][col].setValue(0);
                            i++;
                        }
                        return false;
                    }
                    //adding to stack for adjacent
                    int rowAdjust = 0;
                    int colAdjust = 1;
                    if(checkCoordinates(row+rowAdjust, col+colAdjust)){
                        s.push(solvedPuzzle[row+rowAdjust][colAdjust+col]);
                    }
                    colAdjust = -1;
                    if(checkCoordinates(row+rowAdjust, col+colAdjust)){
                        s.push(solvedPuzzle[row+rowAdjust][colAdjust+col]);
                    }
                    colAdjust = 0;
                    rowAdjust = 1;
                    if(checkCoordinates(row+rowAdjust, col+colAdjust)){
                        s.push(solvedPuzzle[row+rowAdjust][colAdjust+col]);
                    }
                    rowAdjust = -1;
                    if(checkCoordinates(row+rowAdjust, col+colAdjust)){
                        s.push(solvedPuzzle[row+rowAdjust][colAdjust+col]);
                    }                
                }
            }
        }
        return true;
    }

    public void setupSolvedPuzzle() {
                
        solvedPuzzle = new FutoshikiSquare[gridsize][gridsize];
        
        for (int row = 0; row < gridsize; row++){
            for (int column = 0; column < gridsize; column++){
                solvedPuzzle[row][column] = new FutoshikiSquare(
                        squares[row][column].isEditable(), 
                        squares[row][column].getValue(), row, column);
            }
        }
    }
    
    public void saveGame(){

            try (FileWriter saveFile = new FileWriter("FutoshikiSave.txt", false)) {
                String s = "";
                for (int i = 0; i < gridsize; i++) {
                    for (int j = 0; j < gridsize; j++) {
                        s += squares[i][j].getValue();  
                        if(squares[i][j].isEditable()){
                            s+= 1;
                        }
                        else{
                            s+= 0;
                        }
                    }
                    s+= "\n";
                }
                for (int i = 0; i < gridsize; i++) {
                    for (int j = 0; j < gridsize-1; j++) {
                        s+= rowConstraints[i][j].getValue();
                    }
                    s+="\n";
                }
                for (int i = 0; i < gridsize-1; i++) {
                    for (int j = 0; j < gridsize; j++) {
                        s+= columnConstraints[j][i].getValue();
                    }
                    s+= "\n";
                }
                for (int i = 0; i < gridsize; i++) {
                    for (int j = 0; j < gridsize; j++) {
                        s+= latinSquare[i][j].getValue();
                    }
                    s+= "\n";
                }
                saveFile.write(s);
                saveFile.close();
                System.out.println("Written to File");
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
            
        
    }
    
    public void loadGame() {
            
        
        try(BufferedReader readFile = new BufferedReader(new FileReader("FutoshikiSave.txt"))){
            
            String s = readFile.readLine();
            System.out.println(s);            
            //int col = 0;
            int k = 0;
            gridsize = s.length()/2;
            squares = new FutoshikiSquare[gridsize][gridsize];
            rowConstraints = new Constraints[gridsize][gridsize - 1];
            columnConstraints = new Constraints[gridsize][gridsize - 1];
            setup();
            //squares input
            for (int row = 0; row < gridsize; row++) {
                for (int col = 0; col < gridsize; col++) {
                    setSquare(row, col, Character.getNumericValue(s.charAt(k)));
                    k++;
                    if(s.charAt(k) == "0".charAt(0)){
                        squares[row][col].setEditable(false);
                    }
                    else{
                        squares[row][col].setEditable(true);
                    }
                    k++;
                } 
                k = 0;
                s = readFile.readLine();
                System.out.println(s);                
            }
            //row constraints input
            int j = 0;
            for (int row = 0; row < gridsize; row++) {
                for (int col = 0; col < gridsize-1; col++) {
                    String str = Character.toString(s.charAt(j));
                    setRowConstraint(row, col, str, rowConstraints, squares);
                    j++;
                }
                s = readFile.readLine();
                System.out.println(s);                
                j = 0;
            }
            //col constraints input
            j = 0;
            for (int row = 0; row < gridsize-1; row++) {
                for (int col = 0; col < gridsize; col++) {
                    String str = Character.toString(s.charAt(j));
                    switch (str) {
                        case "v":
                            str = ">";
                            break;
                        case "^":
                            str = "<";
                            break;
                        default:
                            str = " ";
                            break;
                    }
                    setColumnConstraint(col, row, str, columnConstraints, squares);
                    j++;
                }
                j =0;
                s = readFile.readLine();
                System.out.println(s);
            }
            j = 0;
            for (int i = 0; i < gridsize; i++) {
                for (int l = 0; l < gridsize; l++) {
                    latinSquare[i][l] = new FutoshikiSquare(true, 
                            Character.getNumericValue(s.charAt(j)) , i, l);
                    j++;
                }
                j = 0;
                s = readFile.readLine();
                System.out.println(s);
            }
            readFile.close();
            

        }
        catch(FileNotFoundException efile){
             System.out.println(efile.toString());
        }  
        catch(IOException e){
            System.out.println(e.toString());
        }
    } 

}
