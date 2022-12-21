package calculator;

import javax.swing.*;
import java.awt.*;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import static calculator.ExpressionToResultConverter.convertPostfixExpressionToResult;
import static calculator.ExpressionToResultConverter.convertInfixToPostfixExpression;
import static calculator.Operator.LEFT_PARENTHESIS;
import static calculator.Operator.RIGHT_PARENTHESIS;
import static calculator.Validator.formatDecimalNumber;
import static calculator.Validator.isValidFirstChar;

public class Calculator extends JFrame {
    //Initialize JDK logger
    private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());

    public Calculator() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculator");
        setSize(350, 550);
        setLayout(null);

        JLabel resultLabel = new JLabel();
        resultLabel.setBounds(60, 10, 250, 50);
        resultLabel.setName("ResultLabel");
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //set greater font size
        resultLabel.setFont(resultLabel.getFont().deriveFont(28f));

        add(resultLabel);

        JLabel equationLabel = new JLabel();
        equationLabel.setBounds(60, 80, 250, 30);
        equationLabel.setName("EquationLabel");
        equationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //set greater Font size
        equationLabel.setFont(equationLabel.getFont().deriveFont(16f));
        //set font color to green
        equationLabel.setForeground(Color.GREEN);
        add(equationLabel);

        //Using Gridlayout for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));

        //First row of buttons
        JButton parenthesesButton = new JButton("()");
        parenthesesButton.setName("Parentheses");

        buttonPanel.add(addCharToLabelField(parenthesesButton, equationLabel, "()"));

        JButton ceButton = new JButton("CE");
        ceButton.setName("CEButton");

        ceButton.addActionListener(e -> {
            String equation = equationLabel.getText();
            if (equation.length() > 0) {
                equationLabel.setText(equation.substring(0, equation.length() - 1));
            }
        });

        buttonPanel.add(ceButton);

        JButton clearBtn = new JButton("C");
        clearBtn.setName("Clear");

        clearBtn.addActionListener(e -> {
            resultLabel.setText("");
            equationLabel.setText("");
            equationLabel.setForeground(Color.GREEN);
        });

        buttonPanel.add(clearBtn);

        JButton delBtn = new JButton("Del");
        delBtn.setName("Delete");

        delBtn.addActionListener(e -> {
            String equation = equationLabel.getText();
            if (equation.length() > 0) {
                equationLabel.setText(equation.substring(0, equation.length() - 1));
            }
        });

        buttonPanel.add(delBtn);

        //Second row of buttons
        JButton powerTwoBtn = new JButton(Operator.POWER_TWO.getSymbol());
        powerTwoBtn.setName(Operator.POWER_TWO.getName());
        //As per requirement, the power of 2 is represented as ^(2)
        buttonPanel.add(addCharToLabelField(powerTwoBtn, equationLabel, Operator.POWER_TWO.getAlternateSymbol()));

        JButton powerYBtn = new JButton(Operator.POWER_Y.getSymbol());
        powerYBtn.setName(Operator.POWER_Y.getName());
        //As per requirement, the power of y is represented as ^(y), where y can be an expression.
        buttonPanel.add(addCharToLabelField(powerYBtn, equationLabel, Operator.POWER_Y.getAlternateSymbol()));

        JButton sqrtBtn = new JButton(Operator.SQUARE_ROOT.getSymbol());
        sqrtBtn.setName(Operator.SQUARE_ROOT.getName());
        buttonPanel.add(addCharToLabelField(sqrtBtn, equationLabel, Operator.SQUARE_ROOT.getSymbol()));

        JButton divideBtn = new JButton(Operator.DIVIDE.getSymbol());
        divideBtn.setName(Operator.DIVIDE.getName());
        buttonPanel.add(addCharToLabelField(divideBtn, equationLabel, Operator.DIVIDE.getSymbol()));


        //Third row of buttons
        JButton sevenBtn = new JButton("7");
        sevenBtn.setName("Seven");
        buttonPanel.add(addCharToLabelField(sevenBtn, equationLabel, "7"));

        JButton eightBtn = new JButton("8");
        eightBtn.setName("Eight");
        buttonPanel.add(addCharToLabelField(eightBtn, equationLabel, "8"));

        JButton nineBtn = new JButton("9");
        nineBtn.setName("Nine");
        buttonPanel.add(addCharToLabelField(nineBtn, equationLabel, "9"));

        JButton multiplyBtn = new JButton(Operator.MULTIPLY.getSymbol());
        multiplyBtn.setName(Operator.MULTIPLY.getName());
        buttonPanel.add(addCharToLabelField(multiplyBtn, equationLabel, Operator.MULTIPLY.getSymbol()));

        //Fourth row of buttons
        JButton fourBtn = new JButton("4");
        fourBtn.setName("Four");
        buttonPanel.add(addCharToLabelField(fourBtn, equationLabel, "4"));

        JButton fiveBtn = new JButton("5");
        fiveBtn.setName("Five");
        buttonPanel.add(addCharToLabelField(fiveBtn, equationLabel, "5"));

        JButton sixBtn = new JButton("6");
        sixBtn.setName("Six");
        buttonPanel.add(addCharToLabelField(sixBtn, equationLabel, "6"));

        JButton minusBtn = new JButton(Operator.SUBTRACT.getSymbol());
        minusBtn.setName(Operator.SUBTRACT.getName());
        buttonPanel.add(addCharToLabelField(minusBtn, equationLabel, Operator.SUBTRACT.getSymbol()));


        //Fifth row of buttons
        JButton oneBtn = new JButton("1");
        oneBtn.setName("One");
        buttonPanel.add(addCharToLabelField(oneBtn, equationLabel, "1"));

        JButton twoBtn = new JButton("2");
        twoBtn.setName("Two");
        buttonPanel.add(addCharToLabelField(twoBtn, equationLabel, "2"));

        JButton threeBtn = new JButton("3");
        threeBtn.setName("Three");
        buttonPanel.add(addCharToLabelField(threeBtn, equationLabel, "3"));

        JButton plusBtn = new JButton(Operator.ADD.getSymbol());
        plusBtn.setName(Operator.ADD.getName());
        buttonPanel.add(addCharToLabelField(plusBtn, equationLabel, Operator.ADD.getSymbol()));


        //Sixth row of buttons
        JButton plusMinusBtn = new JButton(Operator.PLUS_MINUS.getSymbol());
        plusMinusBtn.setName(Operator.PLUS_MINUS.getName());
        plusMinusBtn.addActionListener(e -> {
            String equation = equationLabel.getText();
//            if (!equation.isEmpty()) {
            if (equation.startsWith(Operator.PLUS_MINUS.getAlternateSymbol())) {
                //We need to remove starting (-
                equationLabel.setText(equation.substring(2));
            } else {
                equationLabel.setText(Operator.PLUS_MINUS.getAlternateSymbol() + equation);
            }
//            }
//            else {
//                equationLabel.setText(Operator.PLUS_MINUS.getAlternateSymbol() + equation);
//            }
        });
        buttonPanel.add(plusMinusBtn);

        JButton zeroBtn = new JButton("0");
        zeroBtn.setName("Zero");
        buttonPanel.add(addCharToLabelField(zeroBtn, equationLabel, "0"));

        JButton dotBtn = new JButton(".");
        dotBtn.setName("Dot");
        buttonPanel.add(addCharToLabelField(dotBtn, equationLabel, "."));

        JButton equalBtn = new JButton("=");
        equalBtn.setName("Equals");

        //Now need to implement a stack of operators and operands
        equalBtn.addActionListener(e -> Calculator.doCalculation(equationLabel, resultLabel));
        buttonPanel.add(equalBtn);
        //Setting the size of the panel
        buttonPanel.setBounds(10, 160, 300, 300);
        //Adding the panel to the frame
        add(buttonPanel);

        setVisible(true);

    }

    private static JButton addCharToLabelField(JButton button, JLabel label, String character) {

        //If label ends with an operator and the new char is an operator use the new character instead
        boolean isOperatorChar = isCharacterAnOperator(character);
        boolean isSquareRootChar = character.equals(Operator.SQUARE_ROOT.getSymbol());
        boolean isParentheses = character.equals("()");

        button.addActionListener(e -> {
            //Adjust expression according to the parentheses
            if (isParentheses) {
                adjustParentheses(label);
            } else if (isLabelTextEndsWithOperator(label)) {
                label.setText(isOperatorChar ?
                        label.getText().substring(0, label.getText().length() - 1) + character
                        : label.getText() + character + (isSquareRootChar ? LEFT_PARENTHESIS.getSymbol() : "")); //In case of a square root we add "("
            } else {
                if (isValidFirstChar(label, character)) {
                    //When a new operator is added check for a missing trailing or leading 0
                    label.setText((isOperatorChar || isSquareRootChar) ?
                            formatDecimalNumber(label.getText(), character) : label.getText() + character);
                    label.setForeground(Color.GREEN);
                } else {
                    //Change the font color of the label to darker red
                    label.setForeground(Color.RED.darker());
                }
            }


        });
        return button;
    }

    private static void adjustParentheses(JLabel label) {
        //Insert a left parenthesis if the number of left parentheses is equal to the number of right parentheses
        // in the expression.
        // Insert a left parenthesis if the last character in the expression is another left bracket,
        // or if the last character in the expression is an operator. Otherwise, insert a right parenthesis.
        // The expression in parentheses should be calculated first.
        String equation = label.getText();
        int leftParentheses = 0;
        int rightParentheses = 0;
        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '(') {
                leftParentheses++;
            } else if (equation.charAt(i) == ')') {
                rightParentheses++;
            }
        }
        if (leftParentheses == rightParentheses) {
            label.setText(equation + "(");
        } else if (equation.endsWith("(") || isLabelTextEndsWithOperator(label)) {
            label.setText(equation + "(");
        } else {
            label.setText(equation + ")");
        }
    }

    private static boolean isLabelTextEndsWithOperator(JLabel label) {
        return label.getText().endsWith(Operator.DIVIDE.getSymbol())
                || label.getText().endsWith(Operator.MULTIPLY.getSymbol())
                || label.getText().endsWith(Operator.ADD.getSymbol())
                || label.getText().endsWith(Operator.SUBTRACT.getSymbol());
    }

    private static boolean isCharacterAnOperator(String character) {
        return character.equals(Operator.DIVIDE.getSymbol())
                || character.equals(Operator.MULTIPLY.getSymbol())
                || character.equals(Operator.ADD.getSymbol())
                || character.equals(Operator.SUBTRACT.getSymbol())
//                || character.startsWith(Operator.SQUARE_ROOT.getSymbol())
                || character.startsWith(Operator.POWER_TWO.getSymbol());
        // Using startsWith because the left parenthesis follows the square root/^ symbol
    }


    private static void doCalculation(JLabel equationLabel, JLabel resultLabel) {
        Deque<String> postfixExprStack = convertInfixToPostfixExpression(equationLabel);

        if (!postfixExprStack.isEmpty()) {
            LOGGER.log(Level.INFO, " {0}", String.valueOf(postfixExprStack));
            String result = convertPostfixExpressionToResult(postfixExprStack);
            resultLabel.setText(result);
        }
    }


}



