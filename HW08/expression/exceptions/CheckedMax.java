package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by Лев on 05.04.2017.
 */
public class CheckedMax extends AbstractBinaryOperator {
    public CheckedMax(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws Exception {
        return;
    }

    protected int apply(final int x, final int y) throws Exception {
        check(x, y);
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }
}
