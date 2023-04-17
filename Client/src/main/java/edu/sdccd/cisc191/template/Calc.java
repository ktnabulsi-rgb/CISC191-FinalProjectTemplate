package edu.sdccd.cisc191.template;

public class Calc {

    public int add(int currentNum, int pendingNum) {
        return pendingNum + currentNum;
    }

    public int subtract(int currentNum, int pendingNum) {
        return currentNum - pendingNum;
    }

    public int multiply(int currentNum, int pendingNum) {
        return currentNum * pendingNum;
    }

    public int divide(int currentNum, int pendingNum) {
        Client client = new Client();
        int finalAnswer = 0;
        if (pendingNum == 0) {
            client.setLblAnswer("Undefined");
        } else {
            finalAnswer = currentNum / pendingNum;
        }
        return finalAnswer;
    }

    public void clear() {
        Client client = new Client();
        client.setTxtCurrentNum("");
        client.setTxtPendingNum("");
        client.setLblAnswer("AnswerBox");

    }

}
