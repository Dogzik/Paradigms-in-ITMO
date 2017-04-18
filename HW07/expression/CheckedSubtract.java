package expression;

import myEcxeptions.OverflowException;

public class CheckedSubtract extends AbstractBinaryOperator {
    public CheckedSubtract(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    protected int apply(final int x, final int y) throws OverflowException {
        check(x, y);
        return x - y;
    }
}
