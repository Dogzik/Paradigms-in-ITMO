package TripleExpression;

public strictfp class Subtract extends AbstractBinaryOperator {
    public Subtract(AnyExpression x, AnyExpression y) {
        super(x, y);
    }

    protected double apply(double x, double y) {
        return x - y;
    }

    protected int apply(int x, int y) {
        return x - y;
    }
}
