package com.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable  {

    enum Operations {
        None {@Override public String getOperation() {return null;}},
        Plus {@Override  public String getOperation() { return "+";}},
        Minus {@Override public String getOperation() {return "-";}},
        Multiply{@Override public String getOperation() {return "*";}},
        Divide{@Override public String getOperation() {return "/";}},
        Negate{@Override public String getOperation() {return "Negate";}},
        Equal{@Override public String getOperation() {return "=";}};
        public abstract String getOperation();
    }

    private StringBuilder summary;
    private StringBuilder number;
//    private StringBuilder command;
    private Operations command;
    private Operations lastCommand;


    private HashMap<String, Character> commandShorts;
    private double summary_0;
    private double isAboutToSum;
    private boolean block;
    private boolean isFirst;
    private boolean afterEqual;
    @FXML
    Button deleteLastBtn, CEBtn, CBtn, invertBtn, pointBtn, equalBtn;
    @FXML
    Button mulBtn, minusBtn, plusBtn, divBtn;
    @FXML
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML
    Label sumLabel, sumActionsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isFirst = true;
        block = true;
        afterEqual = false;
        summary_0 = 0;
        isAboutToSum = 0;
        summary = new StringBuilder();
        number = new StringBuilder("0");
        command = Operations.None;
        summary.append("0");
        sumActionsLabel.setText("");
        sumLabel.setText(summary.toString());
        commandShorts = new HashMap<>();
        commandShorts.put("Plus", '+');
        commandShorts.put("Minus", '-');
        commandShorts.put("Divide", '/');
        commandShorts.put("Multiply", '*');
    }

    public void processCommand() {
        if (isFirst) {
            summary_0 = Double.parseDouble(sumLabel.getText());
            lastCommand = command;
            setActionText();
            block = true;
            isFirst = false;
            return;
        }
        if (block) {
            lastCommand = command;
            if (lastCommand.getOperation().equals("Negate")) {
                summary_0 = -summary_0;
                setActionText();
                setIsAboutToSumText();
            }
            setActionText();
            return;
        }
        if (afterEqual) {
            lastCommand = command;
            afterEqual = false;
            return;
        }
        isAboutToSum = Double.parseDouble(sumLabel.getText());
        if (command.getOperation().equals("Multiply") && isAboutToSum == 0) {
            sumLabel.setText("NaN");
            //removeAllActionBtn
        }
        operateCommand();
        lastCommand = command;
        setActionText();
        setIsAboutToSumText();
        block = true;
    }

    public void operateCommand() {
        switch (lastCommand) {
            case None -> summary_0 = BigDecimal.valueOf(isAboutToSum).doubleValue();
            case Plus -> summary_0 = BigDecimal.valueOf(summary_0 + isAboutToSum).doubleValue();
            case Minus -> summary_0 = BigDecimal.valueOf(summary_0 - isAboutToSum).doubleValue();
            case Multiply -> summary_0 = BigDecimal.valueOf(summary_0 * isAboutToSum).doubleValue();
            case Divide -> summary_0 = BigDecimal.valueOf(summary_0 / isAboutToSum).doubleValue();
            case Negate -> summary_0 = -summary_0;
        }
    }

    public void setActionText() {
        if (lastCommand.getOperation().equals("Negate")) {
            sumActionsLabel.setText("Negate(" + (-summary_0)+")");
            return;
        }
        sumActionsLabel.setText(BigDecimal.valueOf(summary_0) + " " + lastCommand.getOperation());
    }

    public void setActionText(String text) {
        sumActionsLabel.setText(text);
    }
    public void nullifyActionText() {
        sumActionsLabel.setText("");
    }

    public void setIsAboutToSumText() {
        sumLabel.setText(BigDecimal.valueOf(summary_0).toString());
    }

    public void nullifyIsAboutToSumText() {
        sumLabel.setText("0");
    }

    public void Minus(ActionEvent actionEvent) {
        command = Operations.Minus;
        processCommand();
    }

    public void Plus(ActionEvent actionEvent) {
        command = Operations.Plus;
        processCommand();
    }

    public void Multiply(ActionEvent actionEvent) {
        command = Operations.Multiply;
        processCommand();
    }

    public void Divide(ActionEvent actionEvent) {
        command = Operations.Divide;
        processCommand();
    }

    public void Point(ActionEvent actionEvent) {
        if (number.toString().contains(".")) {
            return;
        }
        if (number.toString().length() != 0) {
            number.append(".");
            sumLabel.setText(number.toString());
        }
    }

    public void Equal(ActionEvent actionEvent) {
        if (isFirst) {
            summary_0 = isAboutToSum;
            setActionText(summary_0 + " =");
        }
        command = Operations.Equal;
        isAboutToSum = Double.parseDouble(sumLabel.getText());
        operateCommand();
        setActionText(summary_0 + " " + lastCommand.getOperation() + " " + isAboutToSum);
        afterEqual = true;
    }

    public void ClearEntrance(ActionEvent actionEvent) {
        isAboutToSum = 0;
        nullifyIsAboutToSumText();
        number.delete(0,number.length()).append(0);
    }

    public void Clear(ActionEvent actionEvent) {
        summary_0 = 0;
        isAboutToSum = 0;
        isFirst = true;
        block = true;
        nullifyActionText();
        setIsAboutToSumText();
    }

    public void Invert(ActionEvent actionEvent) {
        if (isFirst) {
            return;
        }
        command = Operations.Negate;
        processCommand();
    }

    public void DeleteLastDigital(ActionEvent actionEvent) {
        if (block) {
            return;
        }
        if (number.length() == 1) {
            if (!number.toString().equals("0")) {
                number.deleteCharAt(0);
                number.append("0");
                sumLabel.setText(summary.toString());
                return;
            } else {
                return;
            }
        } else if (number.length() == 2 && number.charAt(0) == '-') {
            number.delete(0,2);
            number.append("0");
            sumLabel.setText(number.toString());
            return;
        } else {
            number.deleteCharAt(number.length()-1);
                sumLabel.setText(number.toString());
        }
    }

    public void AddDigit(int digit) {
        if (block) {
            number.delete(0, number.length());
            block = false;
        }
        if (number.toString().equals("0")) {
            number.delete(0, 1);
        }
        number.append(digit);
        sumLabel.setText(number.toString());
    }

    public void AddZero() {AddDigit(0);}
    public void AddOne() {AddDigit(1);}
    public void AddTwo() {AddDigit(2);}
    public void AddThree() {AddDigit(3);}
    public void AddFour() {AddDigit(4);}
    public void AddFive() {AddDigit(5);}
    public void AddSix() {AddDigit(6);}
    public void AddSeven() {AddDigit(7);}
    public void AddEight() {AddDigit(8);}
    public void AddNine() {AddDigit(9);}
}