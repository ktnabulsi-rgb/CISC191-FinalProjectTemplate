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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;


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
    private static LinkedList<String> calcHistory = new LinkedList<>();

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
            //TODO make new calchistory object and use that inside if statements

            //get text of the button (operator)
            //quit button
            if (event.getSource() == buttonStorage[2][0]) {
                //TODO - send history to server before platforrm exits
                //IOHelper.writeData(calcHistory);
                Client client = new Client();
                client.sendSaveHistoryRequest();
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

            int finalAnswer;
            if (event.getSource() == buttonStorage[2][1]) {
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("AnswerBox");
                previousAnswer.setText(calcHistory.getLast());
            }
            // addition button
            else if (event.getSource() == buttonStorage[0][0]) {
                finalAnswer = calc.add(currentNum, pendingNum);

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
                } else {
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
        try {
            previousAnswer.setText(calcHistory.getLast());
        }
        catch (NoSuchElementException nsee) {
            System.out.println("No existing history");
        }

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

    public void sendSaveHistoryRequest() {
        try {
            Socket socket = new Socket("localhost", 4999);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Save History Request");
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4999);
        PrintWriter pr = new PrintWriter((s.getOutputStream()), true);
        pr.println(HistoryRequest.HR);
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String read;


        while((read = bf.readLine()) != null) {
            System.out.println(read);
            System.out.println("Read Successful");
            calcHistory.add(read);
        }

        //launch();

        //TODO On exit send history to server for persistance

        //run code
    }



}