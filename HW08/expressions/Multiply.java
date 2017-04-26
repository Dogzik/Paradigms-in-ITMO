package expressions;

import exceptions.OverflowException;
import operations.Operation;

/**
 * Created by Лев on 11.04.2017.
 */
public class Multiply<T> extends AbstractBinaryOperator<T> {
    public Multiply(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws OverflowException {
        return op.mul(x, y);
    }
}
