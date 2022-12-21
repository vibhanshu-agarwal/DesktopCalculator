package calculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import static calculator.Validator.*;

public class ExpressionToResultConverter {


    private static final String ADD_UNICODE = "\u002B";
    private static final String SUBTRACT_UNICODE = "-";
    private static final String MULTIPLY_UNICODE = "\u00D7";
    private static final String DIV_UNICODE = "\u00F7";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    private static final String SQUARE_ROOT_UNICODE = "\u221A";

    private static final String POWER_TWO_OPERATOR = "^2";
    private static final String POWER_OPERATOR = "^";
    public static final String INVALID_EXPRESSION = "Invalid expression";

    private ExpressionToResultConverter() {
        //private constructor to prevent instantiation
    }

    private static final Logger LOGGER = Logger.getLogger(ExpressionToResultConverter.class.getName());

    protected static Deque<String> convertInfixToPostfixExpression(JLabel equationLabel) {
        String equation = equationLabel.getText();

        if (isEquationHasErrors(equationLabel, equation))
            return new ArrayDeque<>();

        equation = formatEquationLabel(equationLabel,
                equation);

        //Using a stack to store the operator and operands
        Deque<Operator> operatorStack = new ArrayDeque<>();
        Deque<String> numsAndOpsStack = new ArrayDeque<>();
        //Using a string builder to store the postfix expression
        StringBuilder number = new StringBuilder();

        //Iterating through the equation
        for (char c : equation.toCharArray()) {
            //If the character is a digit, add it to the postfix expression
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else if (c == LEFT_PARENTHESIS.charAt(0)) {
                operatorStack.push(Operator.LEFT_PARENTHESIS);
            } else if (c == RIGHT_PARENTHESIS.charAt(0)) {
                number = processRightParenthesis(operatorStack,
                        numsAndOpsStack,
                        number);
            } else {
                number = processCalculationOperator(operatorStack,
                        numsAndOpsStack,
                        number,
                        c);
            }

        }
        //The last number was not being added to the postfix expression
        addLastOperandToPostfixExpression(numsAndOpsStack,
                number);
        //Any remaining operators
        addRemainingOperatorsToPostfixExpression(equationLabel,
                operatorStack,
                numsAndOpsStack);

        LOGGER.log(Level.INFO,
                " {0}",
                String.valueOf(numsAndOpsStack));

        return numsAndOpsStack;
    }

    private static StringBuilder processCalculationOperator(Deque<Operator> operatorStack, Deque<String> numsAndOpsStack, StringBuilder number, char c) {
        //Add the operand to the postfix expression.
        number = addOperandToPostfixExpression(numsAndOpsStack,
                number);
        Operator operator = Operator.getOperator(c);
        while (!operatorStack.isEmpty() && operatorStack.peek().getPrecedence() >= operator.getPrecedence()) {
            numsAndOpsStack.push(operatorStack.pop().getSymbol());
        }
        operatorStack.push(operator);
        return number;
    }

    private static StringBuilder processRightParenthesis(Deque<Operator> operatorStack, Deque<String> numsAndOpsStack, StringBuilder number) {
        //Add the operand to the postfix expression.
        number = addOperandToPostfixExpression(numsAndOpsStack,
                number);
        while (!operatorStack.isEmpty() && operatorStack.peek() != Operator.LEFT_PARENTHESIS) {
            numsAndOpsStack.push(operatorStack.pop().getSymbol());
        }
        if (!operatorStack.isEmpty())  operatorStack.pop();
        return number;
    }

    private static boolean isEquationHasErrors(JLabel equationLabel, String equation) {
        if (!isValidDivision(equation) || isEquationEndsWithOperator(equation) || !isValidParenthesis(equation) || !isValidSquareRoot(equation)) {
            equationLabel.setForeground(Color.RED.darker());
            return true;
        } else {
            equationLabel.setForeground(Color.GREEN);
        }
        return false;
    }




    private static void addRemainingOperatorsToPostfixExpression(JLabel equationLabel, Deque<Operator> operatorStack, Deque<String> numsAndOpsStack) {
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == Operator.LEFT_PARENTHESIS) {
                equationLabel.setForeground(Color.RED.darker());
                LOGGER.log(Level.SEVERE,
                        INVALID_EXPRESSION);
                throw new IllegalArgumentException(INVALID_EXPRESSION);
            }
            numsAndOpsStack.push(operatorStack.pop().getSymbol());
        }
    }

    private static void addLastOperandToPostfixExpression(Deque<String> numsAndOpsStack, StringBuilder number) {
        if (!number.isEmpty()) {
            numsAndOpsStack.push(number.toString());
        }
    }

    private static StringBuilder addOperandToPostfixExpression(Deque<String> numsAndOpsStack, StringBuilder number) {
        if (!number.isEmpty()) {
            numsAndOpsStack.push(number.toString());
            number = new StringBuilder();
        }
        return number;
    }

    protected static String convertPostfixExpressionToResult(Deque<String> postfixExprStack) {
        //Convert postfix expression to result
        Deque<Double> stack = new ArrayDeque<>();

        //Iterating through the postfix expression as a queue
        while (!postfixExprStack.isEmpty()) {
            final String operatorOrNum = postfixExprStack.pollLast(); //V. important to use pollLast() here

            switch (operatorOrNum) {
                case ADD_UNICODE -> {
                    double num2 = stack.pop();
                    double num1 = stack.pop();
                    stack.push(num1 + num2);
                }
                case SUBTRACT_UNICODE -> {
                    double num2 = stack.pop();
                    //Also, accounting for negation operator here
                    if(stack.isEmpty()) {
                        stack.push(-num2);
                    } else {
                        double num1 = stack.pop();
                        stack.push(num1 - num2);
                    }
                }
                case MULTIPLY_UNICODE -> {
                    double num2 = stack.pop();
                    double num1 = stack.pop();
                    stack.push(num1 * num2);
                }
                case DIV_UNICODE -> {
                    double num2 = stack.pop();
                    double num1 = stack.pop();
                    stack.push(num1 / num2);
                }
                case LEFT_PARENTHESIS, RIGHT_PARENTHESIS -> {
                    LOGGER.log(Level.SEVERE,
                            INVALID_EXPRESSION);
                    return null;
                }
                case SQUARE_ROOT_UNICODE -> {
                    //Important. Only a single operand in this case
                    double num1 = stack.pop();
                    stack.push(Math.sqrt(num1));
                }

                case POWER_TWO_OPERATOR -> {
                    //Important. Only a single operand in this case
                    double num1 = stack.pop();
                    stack.push(Math.pow(num1, 2));
                }

                case POWER_OPERATOR -> {
                    double num2 = stack.pop();
                    double num1 = stack.pop();
                    stack.push(Math.pow(num1, num2));
                }
                default -> stack.push(Double.parseDouble(operatorOrNum));
            }


        }

        String result = String.valueOf(stack.pop());
        if (result.endsWith(".0")) {
            result = result.substring(0,
                    result.length() - 2);
        }
        return result;
    }


}