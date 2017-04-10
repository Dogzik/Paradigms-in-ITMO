package TripleExpression;


public strictfp class Multiply extends AbstractBinaryOperator {
    public Multiply(AnyExpression x, AnyExpression y) {
        super(x, y);
    }

    protected double apply(double x, double y) {
        return x * y;
    }

    protected int apply(int x, int y) {
        return x * y;
    }
}
