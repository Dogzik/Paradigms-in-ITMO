package expression.generic.operations;

import com.sun.deploy.ref.Helpers;
import expression.generic.exceptions.IllegalOperationException;
import expression.generic.exceptions.IncorrectConstException;

/**
 * Created by Лев on 11.04.2017.
 */

public class ByteOperation implements Operation<Byte> {
    private Byte makeByte(final int x) {
        Integer temp = x & 0XFF;
        return temp.byteValue();
    }
    
    public Byte parseNumber(final String number) throws IncorrectConstException {
        try {
            return Byte.parseByte(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    public Byte add(final Byte x, final Byte y) {
        return makeByte(x + y);
    }

    public Byte sub(final Byte x, final Byte y) {
        return makeByte(x - y);
    }

    public Byte mul(final Byte x, final Byte y) {
        return makeByte(x * y);
    }

    public Byte div(final Byte x, final Byte y) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        return makeByte(x / y);
    }

    public Byte mod(final Byte x, final Byte y) throws IllegalOperationException {
        if (y == 0) {
            throw new expression.generic.exceptions.IllegalOperationException("Taking a number module by zero module");
        }
        return makeByte(x % y);
    }

    public Byte not(final Byte x) {
        return makeByte(-x);
    }

    public Byte abs(final Byte x) {
        return makeByte(Math.abs(x));
    }
}
