package expression.generic.operations;

import expression.generic.exceptions.IncorrectConstException;

/**
 * Created by Лев on 11.04.2017.
 */
public class DoubleOperation implements Operation<Double> {
    public Double parseNumber(final String number) throws IncorrectConstException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException NFM) {
            throw new IncorrectConstException();
        }
    }

    public Double add(final Double x, final Double y) {
        return x + y;
    }

    public Double sub(final Double x, final Double y) {
        return x - y;
    }

    public Double mul(final Double x, final Double y) {
        return x * y;
    }

    public Double div(final Double x, final Double y) {
        return x / y;
    }

    public Double mod(final Double x, final Double y) {
        return x % y;
    }

    public Double not(final Double x) {
        return -x;
    }

    public Double abs(final Double x) {
        return Math.abs(x);
    }
}
