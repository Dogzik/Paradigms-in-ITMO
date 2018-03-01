// n - count of elements in sequence
// a[i] - sequence

public class ArrayDequeADT {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private Object[] elements = new Object[20];
    private int size = 0;
    private int left = 0;
    private int right = 0;

    // Pre: (elements.length != 0) && (0 <= x < elements.length) && (que != null)
    private static int dec(ArrayDequeADT que, int x) {
        if (x == 0) {
            return que.elements.length - 1;
        } else {
            return x - 1;
        }
    }
    // Result: x' = (x + 1) % elements.length

    // Pre: (elements.length != 0) && (0 <= x < elements.length) && (que != null)
    private static int add(ArrayDequeADT que, int x) {
        return (x + 1) % que.elements.length;
    }
    // Post: (Result == x - 1 && x > 0) || (Result == elements.length && x == 0)

    //Pre: (sz >= 0) && (que != null)
    private static void ensureCapacity(ArrayDequeADT que, int sz) {
        if ((que.elements.length <= sz) || (sz * 4 < que.elements.length)) {
            Object[] temp = new Object[sz * 2 + 1];
            int ind = 0;
            for (int i = que.left; i != que.right; i = add(que, i)) {
                temp[ind++] = que.elements[i];
            }
            que.elements = temp;
            que.left = 0;
            que.right = ind;
        }
    }
    // Post: (sz < elem'.length <= sz * 4) && (n' == n) && (a'[i] == a[i] for i = 0...n - 1)

    // Pre: (elem != null) && (que != null)
    public static void enqueue(ArrayDequeADT que, Object elem) {
        assert elem != null;
        encuseCapacity(que, que.size + 1);
        que.size++;
        que.elements[que.right] = elem;
        que.right = add(que, que.right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: (elem != null) && (que != null)
    public static void push(ArrayDequeADT que, Object elem) {
        assert elem != null;
        encuseCapacity(que, ++que.size);
        que.left = dec(que, que.left);
        que.elements[que.left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: (n > 0) && (que != null)
    public static Object element(ArrayDequeADT que) {
        assert que.size > 0;

        return que.elements[que.left];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[0])

    // Pre: (n > 0) && (que != null)
    public static Object peek(ArrayDequeADT que) {
        assert que.size > 0;
        return que.elements[dec(que, que.right)];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[n - 1])

    // Pre: (n > 0) && (que != null)
    public static Object dequeue(ArrayDequeADT que) {
        assert que.size > 0;
        Object ans = que.elements[que.left];
        que.left = add(que, que.left);
        encuseCapacity(que, --que.size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (Result == a[0])

    // Pre: (n > 0) && (que != null)
    public static Object remove(ArrayDequeADT que) {
        assert que.size > 0;
        que.right = dec(que, que.right);
        Object ans = que.elements[que.right];
        encuseCapacity(que, --que.size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (Result == a[n - 1])

    // Pre: que != null
    public static int size(ArrayDequeADT que) {
        return que.size;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == n)

    // Pre: que != null
    public static boolean isEmpty(ArrayDequeADT que) {
        return que.size == 0;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == (n == 0))

    // Pre: que != null
    public static void clear(ArrayDequeADT que) {
        encuseCapacity(que, 1);
        que.size = que.left = que.right = 0;
    }
    // Post: n == 0
}
