package edu.sdccd.cisc191.template;

import java.util.LinkedList;

public class Calculator {
    private LinkedList<String> calcHistory;
    private int maxAnswer;
    private int minAnswer;

    public Calculator() {
        calcHistory = new LinkedList<>();
        calcHistory.add("No previous answers");
        maxAnswer = Integer.MIN_VALUE;
        minAnswer = Integer.MAX_VALUE;
    }

    public int add(int num1, int num2) {
        int answer = num1 + num2;
        updateCalculationHistory(answer);
        return answer;
    }

    public int subtract(int num1, int num2) {
        int answer = num1 - num2;
        updateCalculationHistory(answer);
        return answer;
    }

    public int multiply(int num1, int num2) {
        int answer = num1 * num2;
        updateCalculationHistory(answer);
        return answer;
    }

    public int divide(int num1, int num2) {
        if (num2 == 0) {
            calcHistory.add("Undefined");
            return Integer.MIN_VALUE;
        }
        int result = num1 / num2;
        updateCalculationHistory(result);
        return result;
    }

    public void updateCalculationHistory(int answer) {
        calcHistory.add(String.valueOf(answer));
        updateMaxMin(answer);
    }

    public void updateMaxMin(int answer) {
        if (answer > maxAnswer) {
            maxAnswer = answer;
        }
        if (answer < minAnswer) {
            minAnswer = answer;
        }
    }

    public String getPreviousCalculation() {
        return calcHistory.getLast();
    }

    public int getMaxAnswer() {
        return maxAnswer;
    }

    public int getMinAnswer() {
        return minAnswer;
    }

}
