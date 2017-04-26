package operations;

import exceptions.IncorrectConstException;

/**
 * Created by Лев on 11.04.2017.
 */
public class FloatOperation implements Operation<Float> {
    public Float parseNumber(final String number) throws IncorrectConstException {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException NFM) {
            throw new IncorrectConstException();
        }
    }

    public Float add(final Float x, final Float y) {
        return x + y;
    }

    public Float sub(final Float x, final Float y) {
        return x - y;
    }

    public Float mul(final Float x, final Float y) {
        return x * y;
    }

    public Float div(final Float x, final Float y) {
        return x / y;
    }

    public Float mod(final Float x, final Float y) {
        return x % y;
    }

    public Float not(final Float x) {
        return -x;
    }

    public Float abs(final Float x) {
        return Math.abs(x);
    }
}
