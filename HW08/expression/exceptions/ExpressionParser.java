package expression.exceptions;

import expression.TripleExpression;
import expression.exceptions.myEcxeptions.*;

public class ExpressionParser implements Parser {
    private Tokenizer myTokenizer;

    private TripleExpression unary() throws ParsingException {
        TripleExpression res;
        switch (myTokenizer.getNextToken()) {
            case NUMBER:
                res = new Const(myTokenizer.getValue());
                myTokenizer.getNextToken();
                break;
            case VARIABLE:
                res = new Variable(myTokenizer.getName());
                myTokenizer.getNextToken();
                break;
            case MINUS:
                res = new CheckedNegate(unary());
                break;
            case ABS:
                res = new CheckedAbs(unary());
                break;
            case SQRT:
                res = new CheckedSqrt(unary());
                break;
            case LOG2:
                res = new CheckedLog(unary(), new Const(2));
                break;
            case POW2:
                res = new CheckedPow(new Const(2), unary());
                break;
            case OPEN_BRACE:
                int pos = myTokenizer.getInd();
                res = minMax();
                if (myTokenizer.getCurrentToken() != Token.CLOSE_BRACE) {
                    throw new MissingClosingParenthesisException(myTokenizer.getExpression(), pos - 1);
                }
                myTokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + myTokenizer.getExpression());
        }
        return res;
    }


    private TripleExpression mulDivMod() throws ParsingException {
        TripleExpression res = unary();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case MUL:
                    res = new CheckedMultiply(res, unary());
                    break;
                case DIV:
                    res = new CheckedDivide(res, unary());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression addSub() throws ParsingException {
        TripleExpression res = mulDivMod();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case ADD:
                    res = new CheckedAdd(res, mulDivMod());
                    break;
                case MINUS:
                    res = new CheckedSubtract(res, mulDivMod());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression minMax() throws ParsingException {
        TripleExpression res = addSub();
        while (true) {
            switch (myTokenizer.getCurrentToken()) {
                case MIN:
                    res = new CheckedMin(res, addSub());
                    break;
                case MAX:
                    res = new CheckedMax(res, addSub());
                    break;
                default:
                    return res;
            }
        }
    }

    public TripleExpression parse(final String s) throws ParsingException {
        myTokenizer = new Tokenizer(s);
        return minMax();
    }
}
