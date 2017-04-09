package expression;

public class LeftShift extends AbstractBinaryOperator {
    public LeftShift(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x << y;
    }
}
