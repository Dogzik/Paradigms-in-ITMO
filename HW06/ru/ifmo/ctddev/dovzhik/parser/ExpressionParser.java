package ru.ifmo.ctddev.dovzhik.parser;

import ru.ifmo.ctddev.dovzhik.expression.*;

public class ExpressionParser implements Parser {
    private String expression;
    private int ind = 0;

    private enum Token {NUMBER, VARIABLE, NOT, ABS, SQUARE, ADD, SUB, DIV, MUL, MOD, LEFT_SHIFT, RIGHT_SHIFT, OPEN_BRACE, CLOSE_BRACE, END, MISTAKE, XOR, AND, OR}

    private Token curToken = Token.MISTAKE;

    private int value;
    private char name;

    private void skipWhiteSpace() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ind++;
        }
    }

    private void nextToken() {
        skipWhiteSpace();
        if (ind >= expression.length()) {
            curToken = Token.END;
            return;
        }
        char ch = expression.charAt(ind);
        switch (ch) {
            case '^':
                curToken = Token.XOR;
                break;
            case '&':
                curToken = Token.AND;
                break;
            case '|':
                curToken = Token.OR;
                break;
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACE) {
                    curToken = Token.SUB;
                } else {
                    curToken = Token.NOT;
                }
                break;
            case '+':
                curToken = Token.ADD;
                break;
            case '*':
                curToken = Token.MUL;
                break;
            case '/':
                curToken = Token.DIV;
                break;
            case '(':
                curToken = Token.OPEN_BRACE;
                break;
            case ')':
                curToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(ch)) {
                    int l = ind;
                    while (ind < expression.length() && Character.isDigit(expression.charAt(ind))) {
                        ind++;
                    }
                    int r = ind;
                    if (ind == expression.length()) {
                        ind++;
                    }
                    value = Integer.parseUnsignedInt(expression.substring(l, r));
                    curToken = Token.NUMBER;
                    ind--;
                } else if (ch == 'x' || ch == 'y' || ch == 'z') {
                    name = ch;
                    curToken = Token.VARIABLE;
                } else if (expression.substring(ind, ind + 2).equals("<<")) {
                    curToken = Token.LEFT_SHIFT;
                    ind++;
                } else if (expression.substring(ind, ind + 2).equals(">>")) {
                    curToken = Token.RIGHT_SHIFT;
                    ind++;
                } else if (expression.substring(ind, ind + 3).equals("abs")) {
                    curToken = Token.ABS;
                    ind += 2;
                } else if (expression.substring(ind, ind + 3).equals("mod")) {
                    curToken = Token.MOD;
                    ind += 2;
                } else if (expression.substring(ind, ind + 6).equals("square")) {
                    curToken = Token.SQUARE;
                    ind += 5;
                } else {
                    curToken = Token.MISTAKE;
                }
        }
        ind++;
    }

    private TripleExpression unary() {
        nextToken();
        TripleExpression res;
        switch (curToken) {
            case NUMBER:
                res = new Const(value);
                nextToken();
                break;
            case VARIABLE:
                res = new Variable(name);
                nextToken();
                break;
            case NOT:
                res = new Not(unary());
                break;
            case ABS:
                res = new Abs(unary());
                break;
            case SQUARE:
                res = new Square(unary());
                break;
            case OPEN_BRACE:
                res = shifts();
                nextToken();
                break;
            default:
                return new Const(0);
        }
        return res;
    }

    private TripleExpression mulDivMod() {
        TripleExpression res = unary();
        do {
            switch (curToken) {
                case MUL:
                    res = new Multiply(res, unary());
                    break;
                case DIV:
                    res = new Divide(res, unary());
                    break;
                case MOD:
                    res = new Mod(res, unary());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression addSub() {
        TripleExpression res = mulDivMod();
        do {
            switch (curToken) {
                case ADD:
                    res = new Add(res, mulDivMod());
                    break;
                case SUB:
                    res = new Subtract(res, mulDivMod());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression and() {
        TripleExpression res = addSub();
        do {
            switch (curToken) {
                case AND:
                    res = new And(res, addSub());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression xor() {
        TripleExpression res = and();
        do {
            switch (curToken) {
                case XOR:
                    res = new Xor(res, and());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression or() {
        TripleExpression res = xor();
        do {
            switch (curToken) {
                case OR:
                    res = new Or(res, xor());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression shifts() {
        TripleExpression res = or();
        do {
            switch (curToken) {
                case LEFT_SHIFT:
                    res = new LeftShift(res, or());
                    break;
                case RIGHT_SHIFT:
                    res = new RightShift(res, or());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    public TripleExpression parse(final String s) {
        ind = 0;
        expression = s;
        curToken = Token.MISTAKE;
        return shifts();
    }
}
