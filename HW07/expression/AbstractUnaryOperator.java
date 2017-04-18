package expression;

import expression.exceptions.myEcxeptions.EvaluatingException;

public abstract class AbstractUnaryOperator implements TripleExpression {
    private final TripleExpression operand;

    protected AbstractUnaryOperator(final TripleExpression x) {
        operand = x;
    }

    protected abstract void check(final int x) throws EvaluatingException;

    protected abstract int apply(final int x) throws EvaluatingException;

    public int evaluate(final int x, final int y, final int z) throws EvaluatingException {
        return apply(operand.evaluate(x, y, z));
    }
}
