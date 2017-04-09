package expression;

public class Multiply extends AbstractBinaryOperator {
    public Multiply(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x * y;
    }
}
