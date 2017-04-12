package expression.generic.expressions;

import expression.generic.exceptions.IllegalOperationException;
import expression.generic.operations.Operation;

/**
 * Created by Лев on 11.04.2017.
 */
public class Mod<T> extends AbstractBinaryOperator<T> {
    public Mod(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws IllegalOperationException {
        return op.mod(x, y);
    }
}
