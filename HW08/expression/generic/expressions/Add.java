package expression.generic.expressions;

import expression.generic.exceptions.OverflowException;
import expression.generic.operations.Operation;

/**
 * Created by Лев on 11.04.2017.
 */
public class Add<T> extends AbstractBinaryOperator<T> {
    public Add(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws OverflowException {
        return op.add(x, y);
    }
}
