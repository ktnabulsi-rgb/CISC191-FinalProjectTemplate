package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Client extends Application {
    //instance variables
    private TextField textFieldCurrentNum;
    private TextField textFieldPendingNum;
    private Label labelAnswer;
    private Label previousAnswer;
    private Label maximumAnswer;
    private Label minimumAnswer;
    private final Button[][] buttonStorage = new Button[3][3];
    private boolean isRunning = false;
    private LinkedList<String> calcHistory = new LinkedList<>();

    public GridPane createButtonGrid() {
        isRunning = true;
        //make a new grid pane object
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
                //if buttons are not null, then add to grid pane
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
            Calculator calc = new Calculator();

            //typecast to get the source of the button that was pressed


            //get text of the button (operator)
            //quit button
            if (event.getSource() == buttonStorage[2][0]) {
                IOHelper.writeData(calcHistory);
                isRunning = false;
                Platform.exit();
            }
            //if num is too big (over 9 places) print to console
            int pendingNum;
            int currentNum;
            try {
                //parse the user input from a String to int
                currentNum = Integer.parseInt(textFieldCurrentNum.getText());
                pendingNum = Integer.parseInt(textFieldPendingNum.getText());
            } catch (NumberFormatException nfe) {
                //if that fails, clear all text and print to console
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("Too Large To Compute");
                nfe.printStackTrace();
                return;
            }
            //clear button

            int finalAnswer; //create final answer variable
            if (event.getSource() == buttonStorage[2][1]) {
                //set text to empty for clear
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("AnswerBox");
                //get the last value from list and set it as previous value
                previousAnswer.setText(calcHistory.getLast());

            }
            // addition button
            else if (event.getSource() == buttonStorage[0][0]) {
                //call add method from Calculator class
                finalAnswer = calc.add(currentNum, pendingNum);

                labelAnswer.setText(String.valueOf(finalAnswer));
                previousAnswer.setText(calcHistory.getLast());
                calcHistory.add(String.valueOf(finalAnswer));

                //if list has more than 2 values to work with
                if (calcHistory.size() > 2) {
                    //get previous sum
                    String previousNum = calcHistory.get(calcHistory.size() - 2);
                    //parse into int so we can add it to calchistory linked list
                    int previousNumInt = Integer.parseInt(previousNum);
                    //check for max value
                    if (finalAnswer > previousNumInt) {
                        //set text label
                        maximumAnswer.setText(String.valueOf(finalAnswer));
                    }
                    //check for min value
                    if (finalAnswer < previousNumInt) {
                        //set text label
                        minimumAnswer.setText(String.valueOf(finalAnswer));
                    }
                }


            }
            // subtract button
            else if (event.getSource() == buttonStorage[0][1]) {
                finalAnswer = calc.subtract(currentNum, pendingNum);

                labelAnswer.setText(String.valueOf(finalAnswer));
                previousAnswer.setText(calcHistory.getLast());
                calcHistory.add(String.valueOf(finalAnswer));

                if (calcHistory.size() > 2) {
                    String previousNum = calcHistory.get(calcHistory.size() - 2);
                    int previousNumInt = Integer.parseInt(previousNum);
                    if (finalAnswer > previousNumInt) {
                        maximumAnswer.setText(String.valueOf(finalAnswer));
                    }
                    if (finalAnswer < previousNumInt) {
                        minimumAnswer.setText(String.valueOf(finalAnswer));
                    }
                }

            }
            //multiply button
            else if (event.getSource() == buttonStorage[1][0]) {
                finalAnswer = calc.multiply(currentNum, pendingNum);

                previousAnswer.setText(calcHistory.getLast());
                labelAnswer.setText(String.valueOf(finalAnswer));
                calcHistory.add(String.valueOf(finalAnswer));

                if (calcHistory.size() > 2) {
                    String previousNum = calcHistory.get(calcHistory.size() - 2);
                    int previousNumInt = Integer.parseInt(previousNum);
                    if (finalAnswer > previousNumInt) {
                        maximumAnswer.setText(String.valueOf(finalAnswer));
                    }
                    if (finalAnswer < previousNumInt) {
                        minimumAnswer.setText(String.valueOf(finalAnswer));
                    }
                }

            }
            // divide button
            else if (event.getSource() == buttonStorage[1][1]) {
                //if its 0/0
                if (pendingNum == 0) {
                    labelAnswer.setText("Undefined");
                    calcHistory.add("Undefined");
                }
                else {
                    finalAnswer = currentNum / pendingNum;
                    previousAnswer.setText(calcHistory.getLast());
                    labelAnswer.setText(String.valueOf(finalAnswer));
                    calcHistory.add(String.valueOf(finalAnswer));
                    if (calcHistory.size() > 2) {
                        String previousNum = calcHistory.get(calcHistory.size() - 2);
                        int previousNumInt = Integer.parseInt(previousNum);
                        if (finalAnswer > previousNumInt) {
                            maximumAnswer.setText(String.valueOf(finalAnswer));
                        }
                        if (finalAnswer < previousNumInt) {
                            minimumAnswer.setText(String.valueOf(finalAnswer));
                        }
                    }
                }
            }

        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //isRunning should not be true bcs we are still rendering
        if (isRunning) {
            throw new Exception("Rendering encountered an error. ");
        }

        try {
            calcHistory = IOHelper.readItems("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //create text fields
        textFieldCurrentNum = new TextField();
        textFieldPendingNum = new TextField();

        minimumAnswer = new Label("MinimumAnswer");
        minimumAnswer.setAlignment(Pos.CENTER);
        maximumAnswer = new Label("MaximumAnswer");
        maximumAnswer.setAlignment(Pos.CENTER);


        //create labels and set alignment
        labelAnswer = new Label("AnswerBox");
        labelAnswer.setAlignment(Pos.CENTER);
        previousAnswer = new Label("Previous Answer");
        previousAnswer.setAlignment(Pos.BOTTOM_RIGHT);

        //set first/last value of linked list to no answers on start.
        previousAnswer.setText(calcHistory.getLast());

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
        HBox hBoxInput = new HBox(80, textFieldCurrentNum, textFieldPendingNum, labelAnswer, previousAnswer, maximumAnswer, minimumAnswer);
        hBoxInput.setPrefHeight(100); // was 50
        hBoxInput.setAlignment(Pos.BOTTOM_CENTER);

        // Create a BorderPane and add the input box and buttons to it
        BorderPane root = new BorderPane();
        root.setTop(hBoxInput);
        // root.setRight(hBoxOutput);
        root.setCenter(createButtonGrid());

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 1000, 300);//width was 800
        stage.setScene(scene);
        stage.setTitle("Simple Integer Calculator");
        stage.show();
        isRunning = true;

    }

    public static void main(String[] args) {
        //run code
        launch();
    }
}