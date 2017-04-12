package expression.exceptions;

import expression.TripleExpression;

public abstract class AbstractBinaryOperator implements expression.TripleExpression {
    private final expression.TripleExpression firstOperand;
    private final expression.TripleExpression secondOperand;

    protected AbstractBinaryOperator(final expression.TripleExpression x, final TripleExpression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract void check(final int x, final int y) throws Exception;

    protected abstract int apply(final int x, final int y) throws Exception;

    public int evaluate(final int x, final int y, final int z) throws Exception{
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}
