package expression.generic.parcer;

import expression.generic.expressions.TripleExpression;
import expression.generic.exceptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}
