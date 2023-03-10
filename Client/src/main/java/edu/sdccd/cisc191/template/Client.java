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
    private TextField textFieldCurrentNum;
    private TextField textFieldPendingNum;
    private Label labelAnswer;
    public Button[][] buttonStorage = new Button[3][3];
    private int currentNum;
    private int pendingNum;
    private int finalAnswer;
    private GridPane buttonPane;

    @Override
    public void start(Stage stage) throws Exception {

        textFieldCurrentNum = new TextField();
        textFieldPendingNum = new TextField();
        labelAnswer = new Label("AnswerBox");
        labelAnswer.setAlignment(Pos.CENTER);

        buttonStorage[0][0] = new Button("+");
        buttonStorage[0][1] = new Button("-");
        buttonStorage[1][0] = new Button("*");
        buttonStorage[1][1] = new Button("/");
        buttonStorage[2][1] = new Button("C");
        buttonStorage[2][0] = new Button("Q");

        Font buttonFont = new Font(15);
        for (Button[] r : buttonStorage) {
            for (Button button : r) {
                if (button != null) {
                    button.setFont(buttonFont);
                }
            }
        }

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

    }

    public GridPane createButtonGrid() {
        buttonPane = new GridPane();
        buttonPane.setHgap(0);
        buttonPane.setVgap(0);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);

        // Create a grid of buttons and add them to the buttonPane
        for (int r = 0; r < buttonStorage.length; r++) {
            for (int c = 0; c < buttonStorage[r].length; c++) {
                Button button = buttonStorage[r][c];
                if (button != null) {
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
            Button source = (Button)event.getSource(); //typecast
            String operator = source.getText();
            // Quit
            if(event.getSource() == buttonStorage[2][0]) {
                Platform.exit();
            }
            //if num is too big
            try {
                currentNum = Integer.parseInt(textFieldCurrentNum.getText());
                pendingNum = Integer.parseInt(textFieldPendingNum.getText());
            }
            catch(NumberFormatException nfe) {
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("Too Large To Compute");
                nfe.printStackTrace();
                return;
            }
            //clear
            if(event.getSource() == buttonStorage[2][1]) {
                textFieldCurrentNum.setText("");
                textFieldPendingNum.setText("");
                labelAnswer.setText("AnswerBox");
            }
            // +
            else if(event.getSource() == buttonStorage[0][0]) {
                finalAnswer = currentNum + pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // -
            else if(event.getSource() == buttonStorage[0][1]) {
                finalAnswer = currentNum - pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // *
            else if(event.getSource() == buttonStorage[1][0]) {
                finalAnswer = currentNum * pendingNum;
                labelAnswer.setText("" + finalAnswer);
            }
            // /
            else if(event.getSource() == buttonStorage[1][1]) {
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

        launch();  // Run this Application.

    }
}

// end start();





// end class HelloWorldFX