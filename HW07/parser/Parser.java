package parser;

import expression.TripleExpression;
import myEcxeptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws ParsingException;
}
