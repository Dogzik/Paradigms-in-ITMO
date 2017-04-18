package expression;

import myEcxeptions.OverflowException;

public class CheckedAdd extends AbstractBinaryOperator {
    public CheckedAdd(final TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    protected int apply(final int x, final int y) throws OverflowException {
        check(x, y);
        return x + y;
    }
}
