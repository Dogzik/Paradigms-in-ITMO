package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;

/**
 * Created by Лев on 11.04.2017.
 */

public class ByteOperation implements Operation<Byte> {
    public Byte parseNumber(final String number) throws IncorrectConstException {
        try {
            return Byte.parseByte(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    private void checkZero(final int y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Byte add(final Byte x, final Byte y) {
        return (byte) (x + y);
    }

    public Byte sub(final Byte x, final Byte y) {
        return (byte) (x - y);
    }

    public Byte mul(final Byte x, final Byte y) {
        return (byte) (x * y);
    }

    public Byte div(final Byte x, final Byte y) throws IllegalOperationException {
        checkZero(y, "Division by zero");
        return (byte) (x / y);
    }

    public Byte mod(final Byte x, final Byte y) throws IllegalOperationException {
        checkZero(y, "Taking module by zero");
        return (byte) (x % y);
    }

    public Byte not(final Byte x) {
        return (byte) (-x);
    }

    public Byte abs(final Byte x) {
        return (byte) (Math.abs(x));
    }
}
