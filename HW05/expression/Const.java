package expression;

public class Const implements Expression {
    private final int value;

    public Const(int x) {
        value = x;
    }

    public int evaluate(int x) {
        return value;
    }
}
