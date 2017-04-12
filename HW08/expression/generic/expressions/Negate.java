package expression.generic.expressions;

import expression.generic.exceptions.OverflowException;
import expression.generic.operations.Operation;

/**
 * Created by Лев on 11.04.2017.
 */
public class Negate<T> extends AbstractUnaryOperator<T> {
    public Negate(final TripleExpression<T> x, final Operation<T> y) {
        super(x, y);
    }

    protected T apply(final T x) throws OverflowException {
        return op.not(x);
    }
}
