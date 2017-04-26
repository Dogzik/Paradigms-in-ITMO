package expressions;

import exceptions.EvaluatingException;

/**
 * Created by Лев on 11.04.2017.
 */
public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws EvaluatingException;
}
