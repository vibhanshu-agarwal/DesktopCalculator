package calculator;

import javax.swing.*;

public class Validator {

    public static final char PLUS_CHAR = '\u002B';
    public static final char MULTIPLY_CHAR = '\u00D7';
    public static final char DIVIDE_CHAR = '\u00F7';
    public static final char SUBTRACT_CHAR = '-';
    public static final char DOT_CHAR = '.';

    private Validator() {
        //private constructor to prevent instantiation
    }

    protected static boolean isEquationEndsWithOperator(String equation) {
        return equation.endsWith(Operator.DIVIDE.getSymbol())
                || equation.endsWith(Operator.MULTIPLY.getSymbol())
                || equation.endsWith(Operator.ADD.getSymbol())
                || equation.endsWith(Operator.SUBTRACT.getSymbol())
                || equation.endsWith(Operator.POWER.getSymbol())
                || equation.endsWith(Operator.SQUARE_ROOT.getSymbol());
    }

    protected static boolean isValidDivision(String equation) {
        if (equation.contains(Operator.DIVIDE.getSymbol())) {
            String[] split = equation.split(Operator.DIVIDE.getSymbol());
            return !split[1].equals("0");
        }
        return true;
    }

    protected static boolean isValidFirstChar(JLabel label, String character) {
        String text = label.getText();
        if (text.isEmpty()) {
            return !character.equals(Operator.DIVIDE.getSymbol())
                    && !character.equals(Operator.MULTIPLY.getSymbol())
                    && !character.equals(Operator.ADD.getSymbol())
                    && !character.equals(Operator.SUBTRACT.getSymbol())
                    && !character.equals(Operator.POWER.getSymbol());
        }
        return true;
    }

    protected static String formatDecimalNumber(String number, String nextChar) {

        int numberLength = number.length();

        //if the next char is a square root append a left parenthesis
        if (nextChar.equals(Operator.SQUARE_ROOT.getSymbol())) {
            nextChar = nextChar + Operator.LEFT_PARENTHESIS.getSymbol();
        }

        //If decimal is the first char then add a 0 before
        if (number.startsWith(".")) {
            return "0" + number + nextChar;
        }

        //If the decimal point is the last character in the string
        if (number.endsWith(".")) {
            return number + "0" + nextChar;
        }

        StringBuilder numberBuilder = new StringBuilder(number);

        for (int i = 1; i < numberLength; i++) {
            padZeroForOperator(number,
                    numberBuilder,
                    i);
        }


        number = numberBuilder.append(nextChar).toString();

        return number;
    }

    private static void padZeroForOperator(String number, StringBuilder numberBuilder, int i) {
        if (number.charAt(i) == DOT_CHAR &&
                (number.charAt(i - 1) == PLUS_CHAR
                        || number.charAt(i - 1) == SUBTRACT_CHAR
                        || number.charAt(i - 1) == MULTIPLY_CHAR
                        || number.charAt(i - 1) == DIVIDE_CHAR)) {
            numberBuilder.insert(i, '0');
        }
    }

    protected static boolean isValidParenthesis(String equation) {
        int leftParenthesisCount = 0;
        int rightParenthesisCount = 0;
        for (char c : equation.toCharArray()) {
            if (c == Operator.LEFT_PARENTHESIS.getSymbol().charAt(0)) {
                leftParenthesisCount++;
            } else if (c == Operator.RIGHT_PARENTHESIS.getSymbol().charAt(0)) {
                rightParenthesisCount++;
            }
        }
        return leftParenthesisCount == rightParenthesisCount;
    }

    protected static boolean isValidSquareRoot(String equation) {
        //If the equation has negation followed by squareroot
        return !equation.contains(Operator.SUBTRACT.getSymbol() + Operator.SQUARE_ROOT.getSymbol());
    }

    protected static String formatEquationLabel(JLabel equationLabel, String equation) {
        //Add leading or trailing 0 to the equation if it starts or ends with a decimal
        if (equation.startsWith(".")) {
            equation = "0" + equation;
        } else if (equation.endsWith(".")) {
            equation = equation + "0";
        } else if (equation.contains(".")) {
            StringBuilder equationBuilder = new StringBuilder(equation);
            for (int i = 1; i < equation.length(); i++) {
                padZeroForOperator(equation,
                        equationBuilder,
                        i);
            }
            equation = equationBuilder.toString();
        }
        //Set the formatted equation to the label
        equationLabel.setText(equation);
        return equation;
    }
}

