package expression;

/**
 * Created by Лев on 29.03.2017.
 */
public class Xor extends AbstractBinaryOperator{
    public Xor(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x ^ y;
    }
}
