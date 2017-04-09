package expression;

public class Abs extends AbstractUnaryOperator {

    public Abs(final TripleExpression x) {
        super(x);
    }

    protected int apply(final int x) {
        return Math.abs(x);
    }
}
