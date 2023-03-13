package edu.sdccd.cisc191.template;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Client extends Application {
    //instance variables
    private TextField textFieldCurrentNum;
    private TextField textFieldPendingNum;
    private Label labelAnswer;
    private final Button[][] buttonStorage = new Button[3][3];
    private boolean isRunning = false;

    @Override
    public void start(Stage stage) throws Exception {
        //isRunning should not be true bcs we are still rendering
        if (isRunning) {
            throw new Exception("Rendering encountered an error. ");
        }
        //create text fields
        textFieldCurrentNum = new TextField();
        textFieldPendingNum = new TextField();
        //create labels and set alignment
        labelAnswer = new Label("AnswerBox");
        labelAnswer.setAlignment(Pos.CENTER);

        //fill buttons accordingly
        buttonStorage[0][0] = new Button("+");
        buttonStorage[0][1] = new Button("-");
        buttonStorage[1][0] = new Button("*");
        buttonStorage[1][1] = new Button("/");
        buttonStorage[2][1] = new Button("C");
        buttonStorage[2][0] = new Button("Q");

        //set font for buttons.
        Font buttonFont = new Font(15);
        for (Button[] r : buttonStorage) {
            for (Button button : r) {
                if (button != null) {
                    button.setFont(buttonFont);
                }
            }
        }

        //set size of text fields for number insertion
        textFieldCurrentNum.setPrefSize(120, 20);
        textFieldPendingNum.setPrefSize(120, 20);


        // Create an HBox to hold the text fields and labels
        HBox hBoxInput = new HBox(5, textFieldCurrentNum, textFieldPendingNum, labelAnswer);
        hBoxInput.setPrefHeight(50);
        hBoxInput.setAlignment(Pos.BOTTOM_CENTER);

//        HBox hBoxOutput = new HBox(5, labelAnswer);
//        hBoxOutput.setPrefHeight(100);
//        hBoxOutput.setAlignment(Pos.CENTER);

        // Create a BorderPane and add the input box and buttons to it
        BorderPane root = new BorderPane();
        root.setTop(hBoxInput);
        // root.setRight(hBoxOutput);
        root.setCenter(createButtonGrid());

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Simple Integer Calculator");
        stage.show();
        isRunning = true;

    }

    public GridPane createButtonGrid() {
        isRunning = true;
        //make a new gridpane object
        GridPane buttonPane = new GridPane();
        //set vertical/horizontal gap
        buttonPane.setHgap(0);
        buttonPane.setVgap(0);
        //set alignment
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);

        // Create a grid of buttons and add them to the buttonPane
        for (int r = 0; r < buttonStorage.length; r++) {
            for (int c = 0; c < buttonStorage[r].length; c++) {
                Button button = buttonStorage[r][c];
                //if buttons are not null, then add to gridpane
                if (button != null) {
                    //set action handler for each button while creating a button logic object to handle logic for each action
                    button.setOnAction(new ButtonLogic());
                    buttonPane.add(button, c, r);
                }
            }
        }

        return buttonPane;
    }



    // Define a handler for button clicks
    public class ButtonLogic implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            isRunning = true;
            //typecast to get the source of the button that was pressed
            int finalAnswer;
            int pendingNum;
            int currentNum;
            //get text of the button (operator)
            //quit button
            if(event.getSource() == buttonStorage[2][0]) {
                Platform.exit();
                isRunning = false;
            }
            //if num is too big (over 9 places) print to console
            try {
                //parse the user input from a String to int
                currentNum = Integer.parseInt(textFieldCurrentNum.getText());
                pendingNum = Integer.parseInt(textFieldPendingNum.getText());
            }
            catch(NumberFormatException nfe) {
                //if that fails, clear all text and print to console
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("Too Large To Compute");
                nfe.printStackTrace();
                return;
            }
            //clear button
            if(event.getSource() == buttonStorage[2][1]) {
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("AnswerBox");
            }
            // addition button
            else if(event.getSource() == buttonStorage[0][0]) {
                finalAnswer = currentNum + pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // subtract button
            else if(event.getSource() == buttonStorage[0][1]) {
                finalAnswer = currentNum - pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // multiply button
            else if(event.getSource() == buttonStorage[1][0]) {
                finalAnswer = currentNum * pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // divide button
            else if(event.getSource() == buttonStorage[1][1]) {
                //if its 0/0
                if(pendingNum == 0) {
                    labelAnswer.setText("Undefined");
                }
                else {
                    finalAnswer = currentNum / pendingNum;
                    labelAnswer.setText("" + finalAnswer);
                }
            }

        }
    }

    public static void main (String[]args){
        //run code
        launch();  // Run this Application.

    }
}

// end start();





// end class HelloWorldFX