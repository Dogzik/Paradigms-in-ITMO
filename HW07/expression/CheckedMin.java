package expression;

import myEcxeptions.EvaluatingException;

/**
 * Created by Лев on 05.04.2017.
 */
public class CheckedMin extends AbstractBinaryOperator {
    public CheckedMin(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws EvaluatingException {
        return;
    }

    protected int apply(final int x, final int y) throws EvaluatingException {
        check(x, y);
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }
}
