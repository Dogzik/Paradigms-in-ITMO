    // n - count of elements in sequence
    // a[i] - sequence

public class ArrayDeque {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private Object[] elements = new Object[20];
    private int size = 0;
    private int left = 0;
    private int right = 0;

    // Pre: (elements.length != 0) && (0 <= x < elements.length)
    private int add(int x) {
        return (x + 1) % elements.length;
    }
    // Result == (x + 1) % elements.length

    // Pre: (elements.length != 0) && (0 <= x < elements.length)
    private int dec(int x) {
        if (x == 0) {
            return elements.length - 1;
        } else {
            return x - 1;
        }
    }
    // Post: (Result == x - 1 && x > 0) || (Result == elements.length && x == 0)

    //Pre: sz >= 0
    private void ensureCapacity(int sz) {
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
    public void enqueue(Object elem) {
        assert elem != null;
        ensureCapacity(++size);
        elements[right] = elem;
        right = add(right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: (elem != null)
    public void push(Object elem) {
        assert elem != null;
        ensureCapacity(++size);
        left = dec(left);
        elements[left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: n > 0
    public Object element() {
        assert size > 0;
        return elements[left];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[0])

    // Pre: n > 0
    public Object peek() {
        assert size > 0;
        return elements[dec(right)];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[n - 1])

    // Pre: n > 0
    public Object dequeue() {
        assert size > 0;
        Object ans = elements[left];
        elements[left] = null;
        left = add(left);
        ensureCapacity(--size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (Result == a[0])

    // Pre: n > 0
    public Object remove() {
        assert size > 0;
        right = dec(right);
        Object ans = elements[right];
        encuseCapacity(--size);
        return ans;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (Result == a[n - 1])

    // Pre: true
    public int size() {
        return size;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == n)

    // Pre: true
    public boolean isEmpty() {
        return size == 0;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == (n == 0))

    // Pre: true
    public void clear() {
        encuseCapacity(1);
        size = left = right = 0;
    }
    // Post: n == 0
}
