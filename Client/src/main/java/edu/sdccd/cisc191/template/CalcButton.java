package edu.sdccd.cisc191.template;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CalcButton extends Button {
    public static Font buttonFont = new Font(15);

    public CalcButton(String newButtonText) {
        super(newButtonText);
        setFont(buttonFont);
    }
}
