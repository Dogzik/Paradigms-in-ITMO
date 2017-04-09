package expression;

public class Square extends AbstractUnaryOperator {

    public Square(final TripleExpression x) {
        super(x);
    }

    protected int apply(final int x) {
        return x * x;
    }
}
