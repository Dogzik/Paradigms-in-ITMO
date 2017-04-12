package expression.generic.expressions;


import expression.generic.exceptions.EvaluatingException;
import expression.generic.operations.Operation;

public abstract class AbstractUnaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> operand;
    protected final Operation<T> op;

    protected AbstractUnaryOperator(final TripleExpression<T> x, final Operation<T> y) {
        operand = x;
        op = y;
    }

    protected abstract T apply(final T x) throws EvaluatingException;

    public T evaluate(final T x, final T y, final T z) throws EvaluatingException{
        return apply(operand.evaluate(x, y, z));
    }
}
