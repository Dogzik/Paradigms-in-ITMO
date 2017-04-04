package ru.ifmo.ctddev.dovzhik.expression;

/**
 * Created by Лев on 29.03.2017.
 */
public class Or extends AbstractBinaryOperator {
    public Or(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x | y;
    }
}
