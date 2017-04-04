package ru.ifmo.ctddev.dovzhik.parser;

import ru.ifmo.ctddev.dovzhik.expression.TripleExpression;

public interface Parser {
    TripleExpression parse(String expression);
}