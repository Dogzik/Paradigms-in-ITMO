package expression;

import expression.exceptions.myEcxeptions.IllegalOperationException;

public class CheckedLog extends AbstractBinaryOperator {
    public CheckedLog(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws IllegalOperationException {
        if (x <= 0) {
            throw new IllegalOperationException("Log form negative");
        }
        if (y <= 0 || y == 1) {
            throw new IllegalOperationException("Incorrect log base");
        }
    }

    protected int apply(final int x, final int y) throws IllegalOperationException {
        check(x, y);
        int l = 0;
        int r = 31;
        while (r - l > 1) {
            int m = (l + r) >> 1;
            try {
                int res = new CheckedPow(new Const(y), new Const(m)).evaluate(0, 0, 0);
                if (res <= x) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (Exception e) {
                r = m;
            }
        }
        return l;
    }
}
