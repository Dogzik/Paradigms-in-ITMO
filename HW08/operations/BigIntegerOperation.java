package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger> {
    public BigInteger parseNumber(final String number) throws IncorrectConstException {
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

    private void checkZero(final BigInteger x, final String reason) throws IllegalOperationException {
        if (x.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException(reason);
        }
    }

    public BigInteger div(final BigInteger x, final BigInteger y) throws IllegalOperationException {
        checkZero(y, "Division by zero");
        return x.divide(y);
    }

    public BigInteger mod(final BigInteger x, final BigInteger y) throws IllegalOperationException {
        checkZero(y, "Taking module by zero");
        return x.remainder(y);
    }

    public BigInteger not(final BigInteger x) {
        return x.negate();
    }

    public BigInteger abs(final BigInteger x) {
        return x.abs();
    }
}
