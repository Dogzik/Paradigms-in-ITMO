package test.expression;

import expression.*;

public class TestExpression {
    public static void main(final String[] args) {
        final int x = Integer.parseInt(args[0]);
        System.out.print(String.valueOf(x) + " * " + String.valueOf(x) + " - 2 * " + String.valueOf(x) + " + 1 = ");
        System.out.println(new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        ).evaluate(x));
    }
}
