package expression.generic;


import java.math.BigInteger;

/**
 * Created by Лев on 11.04.2017.
 */
public class MyTest {
    public static void main(String[] args) {
        GenericTabulator tab = new GenericTabulator();
        try {
            Object [][][] ans = tab.tabulate("i", "square -5", -8, -8, -6, -6, -18, -18);
        } catch (Exception c) {
            System.out.print("Gotcha");
        }

    }
}
