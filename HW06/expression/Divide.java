package expression;

public class Divide extends AbstractBinaryOperator {
    public Divide(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x / y;
    }
}
