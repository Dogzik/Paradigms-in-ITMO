package expression;

public class Mod extends AbstractBinaryOperator {
    public Mod(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x % y;
    }
}
