package expression;

public class Multiply extends AbstractBinaryOperator {
    public Multiply(Expression x, Expression y) {
        super(x, y);
    }

    protected int Apply(int x, int y) {
        return x * y;
    }
}
