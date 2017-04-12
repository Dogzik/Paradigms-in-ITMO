package expression.generic;

import expression.generic.exceptions.*;
import expression.generic.expressions.TripleExpression;
import expression.generic.operations.*;
import expression.generic.parcer.*;

import java.util.HashMap;

/**
 * Created by Лев on 11.04.2017.
 */

public class GenericTabulator implements Tabulator {
    private final static HashMap<String, Operation<?>> MODES = new HashMap<>();
    static {
        MODES.put("i", new IntegerOperation(true));
        MODES.put("u", new IntegerOperation(false));
        MODES.put("bi", new BigIntegerOperation());
        MODES.put("b", new ByteOperation());
        MODES.put("d", new DoubleOperation());
        MODES.put("f", new FloatOperation());
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws UnknownModeException {
        Operation<?> op = getOperation(mode);
        return makeTable(op, expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(final String mode) throws UnknownModeException {
        if (!MODES.containsKey(mode)) {
            throw new UnknownModeException(mode);
        }
        return MODES.get(mode);
    }

    private <T> Object[][][] makeTable(Operation<T> op, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<>(op);
        TripleExpression<T> exp;
        try {
            exp = parser.parse(expression);
        } catch (Exception e) {
            return res;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = exp.evaluate(op.parseNumber(Integer.toString(i)), op.parseNumber(Integer.toString(j)), op.parseNumber(Integer.toString(k)));
                    } catch (ParsingException | EvaluatingException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }
}
