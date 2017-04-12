package expression.generic.parcer;

import expression.generic.expressions.*;
import expression.generic.exceptions.*;
import expression.generic.operations.Operation;

public class ExpressionParser<T> implements Parser<T> {
    private Tokenizer<T> myTokenizer;
    private Operation<T> myOp;

    public ExpressionParser(final Operation<T> x) {
        myOp = x;
    }

    private TripleExpression<T> unary() throws ParsingException {
        TripleExpression<T> res;
        switch (myTokenizer.getNextToken()) {
            case NUMBER:
                res = new Const<T>(myTokenizer.getValue());
                myTokenizer.getNextToken();
                break;
            case VARIABLE:
                res = new Variable<T>(myTokenizer.getName());
                myTokenizer.getNextToken();
                break;
            case NOT:
                res = new Negate<T>(unary(), myOp);
                break;
            case ABS:
                res = new Abs<T>(unary(), myOp);
                break;
            case SQR:
                res = new Square<T>(unary(), myOp);
                break;
            case OPEN_BRACE:
                res = addSub();
                if (myTokenizer.getCurrentToken() != Token.CLOSE_BRACE) {
                    throw new MissingClosingParenthesisException(myTokenizer.getExpression(), myTokenizer.getInd());
                }
                myTokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + myTokenizer.getExpression());
        }
        return res;
    }

    private TripleExpression<T> mulDivMod() throws ParsingException {
        TripleExpression<T> res = unary();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case MOD:
                    res = new Mod<T>(res, unary(), myOp);
                    break;
                case MUL:
                    res = new Multiply<T>(res, unary(), myOp);
                    break;
                case DIV:
                    res = new Divide<T>(res, unary(), myOp);
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression<T> addSub() throws ParsingException {
        TripleExpression<T> res = mulDivMod();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case ADD:
                    res = new Add<T>(res, mulDivMod(), myOp);
                    break;
                case SUB:
                    res = new Subtract<T>(res, mulDivMod(), myOp);
                    break;
                default:
                    return res;
            }
        } while (true);
    }


    public TripleExpression<T> parse(final String s) throws ParsingException {
        myTokenizer = new Tokenizer<T>(s, myOp);
        return addSub();
    }
}
