package expression;

import expression.exceptions.myEcxeptions.IllegalOperationException;
import expression.exceptions.myEcxeptions.OverflowException;

public class CheckedPow extends AbstractBinaryOperator {
    public CheckedPow(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected void internalCheck(final int x, final int y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    protected void check(final int x, final int y) throws IllegalOperationException {
        if (x == 0 && y == 0) {
            throw new IllegalOperationException("0 ** 0 is not determinated");
        }
        if (x != 1 && x != -1 && y < 0) {
            throw new IllegalOperationException("Powering in negative");
        }
    }

    protected int apply(final int x, final int y) throws OverflowException, IllegalOperationException {
        check(x, y);
        if (y < 0) {
            switch (x) {
                case 1:
                    return 1;
                case -1:
                    if (y % 2 == -1) {
                        return -1;
                    } else {
                        return 1;
                    }
                default:
                    return 0;
            }
        }
        int n = 0;
        int res = 1;
        while (n != y) {
            if (n == 0) {
                internalCheck(res, x);
                res *= x;
                n++;
                continue;
            }
            if (n * 2 <= y) {
                internalCheck(res, res);
                res *= res;
                n <<= 1;
            } else {
                internalCheck(res, x);
                res *= x;
                n++;
            }
        }
        return res;
    }
}
