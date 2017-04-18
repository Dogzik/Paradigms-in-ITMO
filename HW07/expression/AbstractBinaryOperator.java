package expression;

import expression.exceptions.myEcxeptions.EvaluatingException;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression firstOperand;
    private final TripleExpression secondOperand;

    protected AbstractBinaryOperator(final TripleExpression x, final TripleExpression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract void check(final int x, final int y) throws EvaluatingException;

    protected abstract int apply(final int x, final int y) throws EvaluatingException;

    public int evaluate(final int x, final int y, final int z) throws EvaluatingException{
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}
