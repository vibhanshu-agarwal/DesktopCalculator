package calculator;

public enum Operator {
    INVALID_OPERATOR( "NONE","Invalid", "",0),
    ADD("\u002B", "Add","",1),
    SUBTRACT("-", "Subtract","",1),
    MULTIPLY("\u00D7", "Multiply", "",2), //unicode for multiplication sign is \u00D7
    DIVIDE("\u00F7", "Divide", "",2),  //unicode for division sign is \u00F7
    LEFT_PARENTHESIS("(", "OpenParenthesis", "",-1), //Important we don't want to pop this off the stack
    RIGHT_PARENTHESIS(")", "CloseParenthesis", "",-1),//Important we don't want to pop this off the stack

    //The different power operators are clunky because of the foolish requirement.
    POWER("^", "Power", "",3),
    POWER_TWO("x\u00B2", "PowerTwo", "^(2)",3),//Any unicode symbol for power of 2 is
    POWER_Y("x^y", "PowerY", "^(", 3), //Unicode symbol for X raised to the power of Y is \u005E
    SQUARE_ROOT("\u221A", "SquareRoot", "",3),//The Unicode symbol for square root is \u221A

    PLUS_MINUS("\u00B1", "PlusMinus","(-",4); //Unicode symbol for plus minus is \u00B1);

    private final String symbol;
    private final String name;



    private final String alternateSymbol;
    private final int precedence;



    Operator(String symbol, String name, String alternateSymbol, int precedence) {
        this.symbol = symbol;
        this.name = name;
        this.alternateSymbol = alternateSymbol;
        this.precedence = precedence;
    }

    public static Operator getOperator(char c) {
        for (Operator operator : Operator.values()) {
            if (operator.getSymbol().equals(String.valueOf(c))) {
                return operator;
            }
        }
        return Operator.INVALID_OPERATOR;
    }

    public String getAlternateSymbol() {
        return alternateSymbol;
    }
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }



}
