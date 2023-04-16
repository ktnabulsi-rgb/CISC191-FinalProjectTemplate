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

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Client extends Application {
    //instance variables
    private TextField textFieldCurrentNum;
    private TextField textFieldPendingNum;
    private Label labelAnswer;
    private Label previousAnswer;
    private final Button[][] buttonStorage = new Button[3][3];
    private boolean isRunning = false;
    private LinkedList calcHistory = new LinkedList();;
    private int finalAnswer;
    private int pendingNum;
    private int currentNum;




    @Override
    public void start(Stage stage) throws Exception {
        //isRunning should not be true bcs we are still rendering
        if (isRunning) {
            throw new Exception("Rendering encountered an error. ");
        }
        try {
            readItems("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //create text fields
        textFieldCurrentNum = new TextField();
        textFieldPendingNum = new TextField();

        //create labels and set alignment
        labelAnswer = new Label("AnswerBox");
        labelAnswer.setAlignment(Pos.CENTER);
        previousAnswer = new Label("Previous Answer");
        previousAnswer.setText("" + calcHistory.getLast());
        previousAnswer.setAlignment(Pos.BOTTOM_RIGHT);
        //calcHistory = new LinkedList();

        //set first/last value of linked list to no answers on start.
        calcHistory.add("No previous answers");

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
        HBox hBoxInput = new HBox(80, textFieldCurrentNum, textFieldPendingNum, labelAnswer, previousAnswer);
        hBoxInput.setPrefHeight(50);
        hBoxInput.setAlignment(Pos.BOTTOM_CENTER);

        // Create a BorderPane and add the input box and buttons to it
        BorderPane root = new BorderPane();
        root.setTop(hBoxInput);
        // root.setRight(hBoxOutput);
        root.setCenter(createButtonGrid());

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 800, 300);
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

    public Label getLblAnswer() {
        return labelAnswer;
    }

    public void setLblAnswer(String str) {
        labelAnswer.setText(str);
    }

    public Label getLblPrevAnswer() {
        return previousAnswer;
    }

    public void setLblPrevAnswer(String str) {
        previousAnswer.setText(str);
    }

    public int getPendingNum() {
        return pendingNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setTxtCurrentNum(String str) {
        textFieldCurrentNum.setText(str);
    }

    public void setTxtPendingNum(String str) {
        textFieldPendingNum.setText(str);
    }


    // Define a handler for button clicks
    public class ButtonLogic implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            isRunning = true;
            //typecast to get the source of the button that was pressed


            //get text of the button (operator)
            //quit button
            if(event.getSource() == buttonStorage[2][0]) {
               writeData();
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
                previousAnswer.setText("" + calcHistory.getLast());
            }
            // addition button
            else if(event.getSource() == buttonStorage[0][0]) {
                finalAnswer = currentNum + pendingNum;
                labelAnswer.setText("" + finalAnswer);
                previousAnswer.setText("" + calcHistory.getLast());
                calcHistory.add("" + finalAnswer);
            }
            // subtract button
            else if(event.getSource() == buttonStorage[0][1]) {
                finalAnswer = currentNum - pendingNum;
                previousAnswer.setText("" + calcHistory.getLast());
                labelAnswer.setText("" + finalAnswer);
                calcHistory.add("" + finalAnswer);
            }

            /*
            if(event.getSource() == buttonStorage[0][1]) {
                int answer = calc.add(currentNum, pendingNum)
             */
            // multiply button
            else if(event.getSource() == buttonStorage[1][0]) {
                finalAnswer = currentNum * pendingNum;
                previousAnswer.setText("" + calcHistory.getLast());
                labelAnswer.setText("" + finalAnswer);
                calcHistory.add("" + finalAnswer);

            }
            // divide button
            else if(event.getSource() == buttonStorage[1][1]) {
                //if its 0/0
                if(pendingNum == 0) {
                    labelAnswer.setText("Undefined");
                    calcHistory.add("Undefined");
                }
                else {
                    finalAnswer = currentNum / pendingNum;
                    previousAnswer.setText("" + calcHistory.getLast());
                    labelAnswer.setText("" + finalAnswer);
                    calcHistory.add("" + finalAnswer);
                }
            }

        }
    }

 void writeData(){
     String file_name = "output.txt";
     try {

         FileWriter fstream = new FileWriter(file_name);
         BufferedWriter out = new BufferedWriter(fstream);

         ListIterator itr = calcHistory.listIterator();
         while (itr.hasNext()) {
             String element = (String) itr.next();
             out.write(element + "\n");
         }

         out.close();
         System.out.println("File created successfully.");

     } catch (Exception e) {
     }
 }
    public  LinkedList<String> readItems(String fileName) throws IOException
    {
        File input = new File(fileName);
        Scanner in = new Scanner(input);
        String file = "";
        int lines = 0;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.readLine() != null) {
            lines++;
        }

        // Copy the contents from the file to a String
//        while (in.hasNext())
//        {
//            file = in.next();
//        }

        // Create the list
        LinkedList<String> list = new LinkedList<String>();
        ListIterator<String> iter = list.listIterator();

        // Copy the String contents to the Linked List
//        while (iter.hasNext())
//        {
            for (int i=0; i<lines; i++)
            {
                System.out.println(input.length());
                file = in.next();
                list.add(file);
            }
       // }
        calcHistory = list;
        System.out.println(list);
        return list;
    }

    public static void main (String[]args){
        //run code
        launch();  // Run this Application.

    }
}