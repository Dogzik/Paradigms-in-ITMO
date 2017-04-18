package expression;

public class Const implements TripleExpression {
    private final Number value;

    public Const(final Number x) {
        value = x;
    }

    public int evaluate(final int x, final int y, final int z) {
        //System.out.println(value.intValue() + "     !    !");
        return value.intValue();
    }
}
