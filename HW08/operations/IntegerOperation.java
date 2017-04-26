package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;
import exceptions.OverflowException;

/**
 * Created by Лев on 11.04.2017.
 */
public class IntegerOperation implements Operation<Integer> {
    private final boolean checked;

    public IntegerOperation(final boolean check) {
        checked = check;
    }

    public Integer parseNumber(final String number) throws IncorrectConstException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    private void checkAdd(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    public Integer add(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkSub(final Integer x, final Integer y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    public Integer sub(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkSub(x, y);
        }
        return x - y;
    }

    private void checkMul(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    public Integer mul(final Integer x, final Integer y) throws OverflowException {
        if (checked) {
            checkMul(x, y);
        }
        return x * y;
    }

    private void checkDiv(final Integer x, final Integer y) throws OverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    private void checkZero(final int y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Integer div(final Integer x, final Integer y) throws IllegalOperationException, OverflowException {
        checkZero(y, "Division by zero");
        if (checked) {
            checkDiv(x, y);
        }
        return x / y;
    }

    public Integer mod(final Integer x, final Integer y) throws IllegalOperationException {
        checkZero(y, "Taking module by zero");
        return x % y;
    }

    private void checkNot(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Integer not(final Integer x) throws OverflowException {
        if (checked) {
            checkNot(x);
        }
        return -x;
    }

    private void checkAbs(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Integer abs(final Integer x) throws OverflowException {
        if (checked) {
            checkAbs(x);
        }
        return Math.abs(x);
    }
}
