package expression;

public class Not extends AbstractUnaryOperator {

    public Not(final TripleExpression x) {
        super(x);
    }

    protected int apply(final int x) {
        return -x;
    }
}
