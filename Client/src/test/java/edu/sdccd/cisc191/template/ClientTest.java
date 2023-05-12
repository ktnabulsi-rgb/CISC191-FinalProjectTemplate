package edu.sdccd.cisc191.template;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    @Test
    public void checkButtonGridStyle() {
        //create new client object
        Client main = new Client();
        //create new buttonpane to test by calling method
        GridPane buttonPane = main.createButtonGrid();
        //check if hgap,vgap, and alignment all match.
        assertEquals(0, buttonPane.getVgap(), 0);
        assertEquals(0, buttonPane.getHgap(), 0);
        assertEquals(Pos.BOTTOM_CENTER, buttonPane.getAlignment());

//        // check if all 6 buttons have been added to the GridPane
//        int numButtons = 0;
//        for (int i = 0; i < buttonPane.getChildren().size(); i++) {
//            if (buttonPane.getChildren().get(i) instanceof Button) {
//                numButtons++;
//            }
//        }
//        assertEquals(6, numButtons);

        // check if the first button is "+"
//        assertTrue(buttonPane.getChildren().get(0) instanceof Button);
//        Button button = (Button) buttonPane.getChildren().get(0);
//        assertEquals("+", button.getText());
    }
}