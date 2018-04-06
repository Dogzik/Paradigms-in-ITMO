package parcer;

import exceptions.ParsingException;
import expressions.TripleExpression;

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}
