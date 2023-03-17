package edu.sdccd.cisc191.template;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class CalcButtonTest extends ApplicationTest {
    @BeforeAll
    static void initJfxRuntime() {
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.validate();
    }
    @Test
    public void checkCalcButtonStyle() {

        CalcButton calcButton = new CalcButton("+");
        assertEquals("+", calcButton.getText());
        assertEquals(15, calcButton.getFont().getSize());
    }
}