package expression;

public abstract class AbstractUnaryOperator implements TripleExpression {
    protected final TripleExpression operand;

    protected AbstractUnaryOperator(final TripleExpression x) {
        operand = x;
    }

    protected abstract int apply(final int x);

    public int evaluate(final int x, final int y, final int z) {
        return apply(operand.evaluate(x, y, z));
    }
}
