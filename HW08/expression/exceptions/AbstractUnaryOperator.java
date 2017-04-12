package expression.exceptions;

import expression.TripleExpression;

public abstract class AbstractUnaryOperator implements expression.TripleExpression {
    private final expression.TripleExpression operand;

    protected AbstractUnaryOperator(final TripleExpression x) {
        operand = x;
    }

    protected abstract void check(final int x) throws Exception;

    protected abstract int apply(final int x) throws Exception;

    public int evaluate(final int x, final int y, final int z) throws Exception{
        return apply(operand.evaluate(x, y, z));
    }
}
