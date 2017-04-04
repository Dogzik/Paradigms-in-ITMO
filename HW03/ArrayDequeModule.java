    // n - count of elements in sequence
    // a[i] - sequence

public class ArrayDequeModule {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private static Object[] elements = new Object[20];
    private static int size = 0;
    private static int left = 0;
    private static int right = 0;

    // Pre: (elements.length != 0) && (0 <= x < elements.length)
    private static int add(int x) {
        return (x + 1) % elements.length;
    }
    // Result: x' = (x + 1) % elements.length

    // Pre: (elements.length != 0) && (0 <= x < elements.length)
    private static int dec(int x) {
        if (x == 0) {
            return elements.length - 1;
        } else {
            return x - 1;
        }
    }
    // Post: (Result == x - 1 && x > 0) || (Result == elements.length && x == 0)

    //Pre: sz >= 0
    private static void encuseCapacity(int sz) {
        if ((elements.length <= sz) || (sz * 4 < elements.length)) {
            Object[] temp = new Object[sz * 2 + 1];
            int ind = 0;
            for (int i = left; i != right; i = add(i)) {
                temp[ind++] = elements[i];
            }
            elements = temp;
            left = 0;
            right = ind;
        }
    }
    // Post: (sz < elem'.length <= sz * 4) && (n' == n) && (a'[i] == a[i] for i = 0...n - 1)

    // Pre: (elem != null)
    public static void enqueue(Object elem) {
        assert elem != null;
        size++;
        encuseCapacity(size);
        elements[right] = elem;
        right = add(right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: (elem != null)
    public static void push(Object elem) {
        assert elem != null;
        encuseCapacity(++size);
        left = dec(left);
        elements[left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: n > 0
    public static Object element() {
        assert size > 0;

        return elements[left];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object peek() {
        assert size > 0;
        return elements[dec(right)];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[n - 1])

    // Pre: n > 0
    public static Object dequeue() {
        assert size > 0;
        Object ans = elements[left];
        left = add(left);
        size--;
        encuseCapacity(size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object remove() {
        assert size > 0;
        right = dec(right);
        Object ans = elements[right];
        encuseCapacity(--size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (Result == a[n - 1])

    // Pre: true
    public static int size() {
        return size;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == n)

    // Pre: true
    public static boolean isEmpty() {
        return size == 0;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == (n == 0))

    // Pre: true
    public void clear() {
        elements = new Object[5];
        size = left = right = 0;
    }
    // Post: n == 0
}