package parser;

import myEcxeptions.*;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Лев on 09.04.2017.
 */
public class Tokenizer {
    private String expression;
    private int ind = 0;

    private Token curToken;
    private static Set<Token> operations = EnumSet.of(Token.SQRT, Token.MINUS, Token.ABS, Token.ADD, Token.SUB, Token.MUL, Token.DIV, Token.LOG2, Token.POW2, Token.MIN, Token.MAX);
    private static final Set<Token> BINARY_OPERATIONS = EnumSet.of(Token.ADD, Token.SUB, Token.MUL, Token.DIV, Token.MIN, Token.MAX);
    private static final Map<String, Token> IDENTIFIERS = new HashMap<>();

    static {
        IDENTIFIERS.put("sqrt", Token.SQRT);
        IDENTIFIERS.put("abs", Token.ABS);
        IDENTIFIERS.put("log2", Token.LOG2);
        IDENTIFIERS.put("pow2", Token.POW2);
        IDENTIFIERS.put("min", Token.MIN);
        IDENTIFIERS.put("max", Token.MAX);
        IDENTIFIERS.put("x", Token.VARIABLE);
        IDENTIFIERS.put("y", Token.VARIABLE);
        IDENTIFIERS.put("z", Token.VARIABLE);
    }

    private int value;
    private char name;
    private int balance;

    public Tokenizer(final String s) {
        expression = s;
        ind = 0;
        curToken = Token.BEGIN;
        balance = 0;
    }

    public Token getNextToken() throws ParsingException {
        nextToken();
        return curToken;
    }

    public Token getCurrentToken() {
        return curToken;
    }

    public int getValue() {
        return value;
    }

    public char getName() {
        return name;
    }

    public int getInd() {
        return ind;
    }

    public String getExpression() {
        return expression;
    }

    private void skipWhiteSpace() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ind++;
        }
    }

    private void checkForOperand(final int pos) throws MissingOperandException {
        if (operations.contains(curToken) || curToken == Token.OPEN_BRACE || curToken == Token.BEGIN) {
            throw new MissingOperandException(expression, pos);
        }
    }

    private void checkForOperation(final int pos) throws MissingOperationException {
        if (curToken == Token.CLOSE_BRACE || curToken == Token.VARIABLE || curToken == Token.NUMBER) {
            throw new MissingOperationException(expression, pos);
        }
    }

    private String getNumber() {
        int l = ind;
        while (ind < expression.length() && Character.isDigit(expression.charAt(ind))) {
            ind++;
        }
        int r = ind;
        ind--;
        return expression.substring(l, r);
    }

    private boolean isPartOfIdentifier(final char x) {
        return Character.isLetterOrDigit(x) || x == '_';
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(ind))) {
            throw new UnknownSymbolException(expression, ind);
        }
        int l = ind;
        while (ind < expression.length() && isPartOfIdentifier(expression.charAt(ind))) {
            ind++;
        }
        int r = ind;
        ind--;
        return expression.substring(l, r);
    }

    private void nextToken() throws ParsingException {
        skipWhiteSpace();
        if (ind >= expression.length()) {
            checkForOperand(ind);
            curToken = Token.END;
            return;
        }
        switch (expression.charAt(ind)) {
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACE) {
                    curToken = Token.SUB;
                } else {
                    if (ind + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, ind + 1);
                    }
                    if (Character.isDigit(expression.charAt(ind + 1))) {
                        ind++;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException NFE) {
                            throw new IllegalConstantException("-" + temp, expression, ind - temp.length());
                        }
                        curToken = Token.NUMBER;
                    } else {
                        curToken = Token.MINUS;
                    }
                }
                break;
            case '+':
                checkForOperand(ind);
                curToken = Token.ADD;
                break;
            case '*':
                checkForOperand(ind);
                curToken = Token.MUL;
                break;
            case '/':
                checkForOperand(ind);
                curToken = Token.DIV;
                break;
            case '(':
                checkForOperation(ind);
                balance++;
                curToken = Token.OPEN_BRACE;
                break;
            case ')':
                if (operations.contains(curToken) || curToken == Token.OPEN_BRACE) {
                    throw new MissingOperandException(expression, ind);
                }
                balance--;
                if (balance < 0) {
                    throw new OddClosingParenthesisException(expression, ind);
                }
                curToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(expression.charAt(ind))) {
                    checkForOperation(ind);
                    String temp = getNumber();
                    try {
                        value = Integer.parseInt(temp);
                    } catch (NumberFormatException NFE) {
                        throw new IllegalConstantException(temp, expression, ind - temp.length() + 1);
                    }
                    curToken = Token.NUMBER;
                } else {
                    String curInd = getIdentifier();
                    if (!IDENTIFIERS.containsKey(curInd)) {
                        throw new UnknownIdentifierException(curInd, expression, ind - curInd.length() + 1);
                    }
                    if (BINARY_OPERATIONS.contains(IDENTIFIERS.get(curInd))) {
                        checkForOperand(ind - curInd.length() + 1);
                    } else {
                        checkForOperation(ind - curInd.length() + 1);
                    }
                    curToken = IDENTIFIERS.get(curInd);
                    if (curToken == Token.VARIABLE) {
                        name = curInd.charAt(0);
                    }
                }

        }
        ind++;
    }
}
