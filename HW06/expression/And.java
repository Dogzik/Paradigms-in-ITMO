package expression;

public class And extends AbstractBinaryOperator {
    public And(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x & y;
    }

}
