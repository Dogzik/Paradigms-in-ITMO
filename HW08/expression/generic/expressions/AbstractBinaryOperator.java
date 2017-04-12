package expression.generic.expressions;

import expression.generic.exceptions.EvaluatingException;
import expression.generic.operations.Operation;

public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstOperand;
    private final TripleExpression<T> secondOperand;
    protected final Operation<T> op;

    protected AbstractBinaryOperator(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        firstOperand = x;
        secondOperand = y;
        op = z;
    }

    protected abstract T apply(final T x, final T y) throws EvaluatingException;

    public T evaluate(final T x, final T y, final T z) throws EvaluatingException {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}
