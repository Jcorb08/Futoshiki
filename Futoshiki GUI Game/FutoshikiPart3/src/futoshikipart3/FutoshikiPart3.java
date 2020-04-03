/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipart3;

import futoshikipart2.Futoshiki;
import futoshikipart2.FutoshikiSquare;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Joe
 */
public class FutoshikiPart3 extends Application {
    
    private GridPane futoshikiGrid;
    private Futoshiki futoshiki;
    private BorderPane root;
    private int difficulty;
    private int sizeInput;
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Futoshiki");
        dialog.setHeaderText("Please select the values for difficulty and size");
        dialog.setResizable(true);
        
        Label label1 = new Label("Size: ");
        Label label2 = new Label("Difficulty: ");
        ComboBox<Integer> combo1 = new ComboBox<>();
        combo1.getItems().addAll(4,5,6,7);
        combo1.setValue(4);
        ComboBox<String> combo2 = new ComboBox<>();
        combo2.getItems().addAll("Random", "Easy", "Medium", "Hard");
        combo2.setValue("Random");
                
        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(combo1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(combo2, 2, 2);
        grid.setHgap(20);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeLoad = new ButtonType("Load", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeLoad);
        
        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                
                return combo1.getValue() + " " + combo2.getValue();
            }
            if (b == buttonTypeLoad){
                
                return "";
            }
            
            return null;
        });

        dialog.showAndWait();
        root = new BorderPane();
        if (!dialog.getResult().equals("")){
            convertSizeAndDifficulty(dialog.getResult());
            System.out.println(sizeInput);
            System.out.println(difficulty);
            setupGrid(1);
        }
        else{
            futoshiki = new Futoshiki();
            futoshiki.loadGame();
            sizeInput = futoshiki.getGridsize();
            futoshikiGrid = new GridPane();
            futoshikiGrid.setAlignment(Pos.CENTER);
            futoshikiGrid.setVgap(sizeInput);
            updateGrid();
        }
        
        root.setCenter(futoshikiGrid);
        root.setTop(setupMenu());
        Scene scene = new Scene(root, 900, 600);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Futoshiki");
        primaryStage.setScene(scene);
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.show();
        stage = primaryStage;
        

    }
    
    private HBox setupMenu(){
        //make HBox with buttons for each option
        HBox hbox = new HBox();
        Button instructionsButton = new Button("Instructions");
        instructionsButton.setOnAction((ActionEvent event) -> {
            System.out.println(event);
            showInstuctions();
        });
        Button saveButton = new Button("Save");
        saveButton.setOnAction((ActionEvent event) -> {
            System.out.println(event);
            futoshiki.saveGame();
        });
        Button loadButton = new Button("Load");
        loadButton.setOnAction((ActionEvent event) -> {
            System.out.println(event);
            futoshiki.loadGame();
            sizeInput = futoshiki.getGridsize();
            futoshikiGrid = new GridPane();
            futoshikiGrid.setAlignment(Pos.CENTER);
            futoshikiGrid.setVgap(sizeInput);
            updateGrid();
            root.setCenter(futoshikiGrid);
            System.out.println(futoshiki.displayString());
        });
        Button retireButton = new Button("Retire");
        retireButton.setOnAction((ActionEvent event) -> {
            System.out.println(event);
            gameEnd(0);
        });
        HBox.setHgrow(instructionsButton, Priority.ALWAYS);
        HBox.setHgrow(loadButton, Priority.ALWAYS);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        HBox.setHgrow(retireButton, Priority.ALWAYS);
        hbox.setMaxHeight(50);
        hbox.setMinHeight(50);
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(instructionsButton,saveButton,loadButton,retireButton);
        return hbox;
    }
    
    private void showInstuctions()
    {
        Dialog<String> instuctionDialog = new Dialog<>();
        instuctionDialog.setTitle("Instuctions");
        instuctionDialog.setResizable(true);
        instuctionDialog.setHeaderText("How to Play!");
        instuctionDialog.setContentText(
            "Futoshiki is a game similar to Sudoku as the numbers need to create a Latin square. \n"
            + "However, there are also constraints to deal with in the square, \n"
            + "which enable the user to solve the puzzle. \n"        
            + "The Greyed Out Tiles are uneditable. \n"
            + "To change a tile click on the one you would like to change. \n"
            + "Keep clicking to cycle through all the number options. \n"
            + "If the number is red afterwards it means that the grid isn't correct. \n"
            + "If the number is green afterwards it means it is a legal move. \n"
            + "");
         ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        instuctionDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        
        instuctionDialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                
                return " ";
            }
            
            return null;
        });

        instuctionDialog.showAndWait();
    }
    
           
            
    private void gameEnd(int choice)
    {
        //0 - gave up
        Dialog<Integer> endDialog = new Dialog<>();
        endDialog.setTitle("Game Over!");
        endDialog.setResizable(true);
        if(choice == 1){
            endDialog.setHeaderText("Congrats! You Win!");
        }
        else{
            endDialog.setHeaderText("Unlucky! You Lose!");
        }
        
        ButtonType buttonTypeRetry = new ButtonType("Retry", ButtonData.OK_DONE);
        endDialog.getDialogPane().getButtonTypes().add(buttonTypeRetry);        
        ButtonType buttonTypeQuit = new ButtonType("Quit", ButtonData.OK_DONE);
        endDialog.getDialogPane().getButtonTypes().add(buttonTypeQuit);
        
        endDialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeRetry){
                
                return 1;
            }
            else if(b == buttonTypeQuit){
                
                return 0;
            }
            
            return null;
        });

        endDialog.showAndWait();
        if(endDialog.getResult() == 0){
            Platform.exit();
        }
        else{
            stage.close();
            Platform.runLater( () -> start( new Stage() ) );
        }
        //1 - success
    }
    
    private void setupGrid(int start)
    {
        futoshikiGrid = new GridPane();
        futoshikiGrid.setAlignment(Pos.CENTER);
        futoshikiGrid.setVgap(sizeInput);
        futoshiki = new Futoshiki(sizeInput);
        if(start == 1){
            boolean solved = false;
            while(!solved){
                futoshiki.fillPuzzle(difficulty, (sizeInput/2)+1, (sizeInput/2)+1);
                futoshiki.setupSolvedPuzzle();
                //re-do this
                solved = futoshiki.solve();
                updateGrid();
            }
        }
    }
    
    private void setupOnclick(Label l, int row, int col, int colGrid, int rowGrid)
    {
            l.setOnMouseClicked((MouseEvent event) -> {
                System.out.println(event);
                int currentValue = futoshiki.getSquare(row, col);
                currentValue++;
                System.out.println(currentValue);
                Label a;
                if(currentValue > futoshiki.getGridsize())
                {
                    currentValue = 0;
                    a = new Label(" ");
                }
                else
                {
                    a = new Label("" + currentValue);
                }
                
                futoshiki.setSquare(row, col, currentValue);
                if(currentValue == 0){
                    a.setTextFill(Color.BLACK);
                    a.setStyle("-fx-border-color: black;\n"
                    + "-fx-border-width: 2;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-padding: 0 0 0 10;\n");
                }
                //futoshiki.isLegal(futoshiki.getSquares(),futoshiki.getRowConstraints(),futoshiki.getColumnConstraints())
                else if(futoshiki.getSolvedPuzzle()[row][col].getValue() == currentValue){
                    a.setTextFill(Color.GREEN);
                    a.setStyle("-fx-border-color: green;\n"
                    + "-fx-border-width: 2;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-padding: 0 0 0 10;\n");
                }
                else {
                    a.setTextFill(Color.RED);
                    a.setStyle("-fx-border-color: red;\n"
                    + "-fx-border-width: 2;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-padding: 0 0 0 10;\n");
                }
                a.setMaxSize(40, 40);
                a.setMinSize(40, 40);
                a.setFont(new Font("Arial", 30));
                
                setupOnclick(a, row, col, colGrid, rowGrid);
                futoshikiGrid.getChildren().remove(l);
                futoshikiGrid.add(a,colGrid,rowGrid);
                if(isPuzzleComplete()){
                     gameEnd(1);
                }  
            });
    }
    
    public boolean isPuzzleComplete(){
            for(int i = 0; i < futoshiki.getGridsize(); i++){
                for(int j = 0; j < futoshiki.getGridsize(); j++){
                    if(futoshiki.getSquares()[i][j].getValue() != futoshiki.getSolvedPuzzle()[i][j].getValue()){
                        return false;
                    }
                }
            }
            return true;
    }
    
    private void updateGrid()
    {
        int row = 0;
        int i;
        for (i = 0; i < (futoshiki.getGridsize()*2)-3; i++) {
            updateRow(row, i);
            i++;
            updateColumnConstraints(row, i);
            row++;
        }
        updateRow(row, i);

    }
    
    private void updateColumnConstraints(int row, int k)
    {
        int j = 0;
        for (int i = 0; i < futoshiki.getGridsize(); i++) {
            
            Label l = new Label(futoshiki.getColumnConstraints()[i][row].getValue());
            l.setMaxSize(40, 40);
            l.setMinSize(40, 40);
            l.setFont(new Font("Arial", 30));
            l.setStyle("-fx-padding: 0 0 0 12.5;\n");
            futoshikiGrid.add(l, j, k, 1, 1);
            j++;
            j++;
        }
    }
    
    private void updateRow(int row, int k)
    {
        int j = 0;
        for (int i = 0; i < futoshiki.getGridsize(); i++) {
            FutoshikiSquare fs = futoshiki.getSquares()[row][i];
            String in;
            if(fs.getValue() == 0){
                in = " ";
            }
            else{
                in = Integer.toString(fs.getValue());
            }
            Label l = new Label(in);
            if(!fs.isEditable()){
                l.setTextFill(Color.GREY);
                l.setStyle("-fx-border-color: grey;\n"
                + "-fx-border-width: 2;\n"
                + "-fx-border-style: solid;\n"
                + "-fx-padding: 0 0 0 10;\n");
            }
            else{
                l.setTextFill(Color.BLACK);
                l.setStyle("-fx-border-color: black;\n"
                + "-fx-border-width: 2;\n"
                + "-fx-border-style: solid;\n"
                + "-fx-padding: 0 0 0 10;\n");
                setupOnclick(l,row,i,j,k);
            }
            l.setMaxSize(40, 40);
            l.setMinSize(40, 40);
            l.setFont(new Font("Arial", 30));
            futoshikiGrid.add(l , j, k, 1,1);
            j++;
            if (i < (futoshiki.getGridsize()-1)) {
                l = new Label(futoshiki.getRowConstraints()[row][i].getValue());
                l.setMaxSize(40, 40);
                l.setMinSize(40, 40);
                l.setFont(new Font("Arial", 30));
                l.setStyle("-fx-padding: 0 0 0 10;\n");
                futoshikiGrid.add(l, j, k, 1,1);

            }
            j++;
        }
    }

    private void convertSizeAndDifficulty(String result) {
        int i = 0;
        String s = "";
        while((result.charAt(i)) != (" ").charAt(0))
        {
            s += result.charAt(i);
            i++;
        }
        sizeInput = Integer.parseInt(s);
        s = "";
        i++;
        for(int j = i; j < result.length(); j++)
        {
            s += result.charAt(j);
        }
        System.out.println(s);
        switch (s) {
            case "Random":
                Random rand = new Random();
                difficulty = rand.nextInt(sizeInput+2)+1;
                break;
            case "Easy":
                difficulty = sizeInput + 2;
                break;
            case "Medium":
                difficulty = sizeInput;
                break;
            case "Hard":
                difficulty = sizeInput - 2;
                break;
            default:
                break;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    
}
