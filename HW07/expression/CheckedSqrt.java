package expression;

import expression.exceptions.myEcxeptions.IllegalOperationException;

/**
 * Created by Лев on 03.04.2017.
 */
public class CheckedSqrt extends AbstractUnaryOperator {
    public CheckedSqrt(final TripleExpression x) {
        super(x);
    }

    protected void check(final int x) throws IllegalOperationException {
        if (x < 0) {
            throw new IllegalOperationException("Sqrt from negative");
        }
    }

    protected int apply(final int x) throws IllegalOperationException {
        check(x);
        int l = 0;
        int r = 46340;
        while (r - l > 1) {
            int m = (l + r) >> 1;
            if (m * m <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
