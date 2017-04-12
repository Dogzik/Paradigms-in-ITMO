package expression.generic.operations;

import expression.generic.exceptions.IllegalOperationException;
import expression.generic.exceptions.IncorrectConstException;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger> {
    public BigInteger parseNumber(final String number) throws IncorrectConstException{
        try {
            return new BigInteger(number);
        } catch (NumberFormatException NFE) {
            throw new IncorrectConstException();
        }
    }

    public BigInteger add(final BigInteger x, final BigInteger y) {
        return x.add(y);
    }

    public BigInteger sub(final BigInteger x, final BigInteger y) {
        return x.subtract(y);
    }

    public BigInteger mul(final BigInteger x, final BigInteger y) {
        return x.multiply(y);
    }

    public BigInteger div(final BigInteger x, final BigInteger y) throws IllegalOperationException {
        if (y.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException("Division by zero");
        }
        return x.divide(y);
    }

    public BigInteger mod(final BigInteger x, final BigInteger y) throws IllegalOperationException {
        if (y.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException("Taking a number module by zero module");
        }
        return x.remainder(y);
    }

    public BigInteger not(final BigInteger x) {
        return x.negate();
    }

    public BigInteger abs(final BigInteger x) {
        return x.abs();
    }
}
