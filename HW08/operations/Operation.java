package operations;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;
import exceptions.OverflowException;

/**
 * Created by Лев on 11.04.2017.
 */
public interface Operation<T> {
    T parseNumber(final String number) throws IncorrectConstException;

    T add(final T x, final T y) throws OverflowException;

    T sub(final T x, final T y) throws OverflowException;

    T mul(final T x, final T y) throws OverflowException;

    T div(final T x, final T y) throws EvaluatingException;

    T mod(final T x, final T y) throws IllegalOperationException;

    T not(final T x) throws OverflowException;

    T abs(final T x) throws OverflowException;

    default T sqr(final T x) throws OverflowException {
        return mul(x, x);
    }
}
