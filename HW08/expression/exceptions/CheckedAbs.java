package expression.exceptions;

import expression.TripleExpression;
import expression.exceptions.myEcxeptions.OverflowException;

public class CheckedAbs extends AbstractUnaryOperator {

    public CheckedAbs(final TripleExpression x) {
        super(x);
    }

    protected void check(final int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply(final int x) throws OverflowException {
        check(x);
        int res = x;
        if (x < 0) {
            res = -res;
        }
        return res;
    }
}
