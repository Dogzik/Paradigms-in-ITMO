package expressions;

import exceptions.EvaluatingException;
import operations.Operation;

/**
 * Created by Лев on 11.04.2017.
 */
public class Divide<T> extends AbstractBinaryOperator<T> {
    public Divide(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T apply(final T x, final T y) throws EvaluatingException {
        return op.div(x, y);
    }
}
