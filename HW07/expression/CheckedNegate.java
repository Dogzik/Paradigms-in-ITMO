package expression;

import myEcxeptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperator {

    public CheckedNegate(final TripleExpression x) {
        super(x);
    }

    protected void check(final int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply(final int x) throws OverflowException {
        check(x);
        return -x;
    }
}
