package edu.sdccd.cisc191.template;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class TimerThread implements Runnable {
    private Label timerLbl;
    private int timerCtr;

    public TimerThread(Label timerLbl) {
        this.timerLbl = timerLbl;
        this.timerCtr = 0;
    }

    @Override
    public void run() {

        new Thread(() -> {
            try {
                while (true) //infinite loop bcs were counting as the app runs
                {
                    String timerTxt = "Application Run Time: " + timerCtr; //print app run time and update
                    timerCtr++; //increase counter everytime for persistence
                    //make sure its running on application thread
                    Platform.runLater(() -> {
                        timerLbl.setText(timerTxt); //reset text to update to specified # of seconds
                    });
                    Thread.sleep(1000); //set 1second

                }
            } catch (InterruptedException ex) {
                //no need for exception here
            }
        }).start();

    }

}
