
public class ArrayQueue extends AbstractQueue {
    private Object[] elements;
    private int left = 0;
    private int right = 0;

    public ArrayQueue(){
        elements = new Object[10];
    }

    public ArrayQueue(int sz) {
        elements = new Object[sz];
    }

    private int inc(int x) {
        return (x + 1) % elements.length;
    }

    private void ensureCapacity(int sz) {
        if ((elements.length <= sz) || (sz * 4 < elements.length)) {
            Object[] temp = new Object[sz * 2 + 1];
            int cnt = 0;
            if (left <= right) {
                cnt = right - left;
                System.arraycopy(elements, left, temp, 0, cnt);
            } else {
                cnt = elements.length - left + right;
                System.arraycopy(elements, left, temp, 0, elements.length - left);
                System.arraycopy(elements, 0, temp, elements.length - left, right);
            }
            elements = temp;
            left = 0;
            right = cnt;
        }
    }

    protected void enqueueImpl(Object elem) {
        ensureCapacity(size + 1);
        elements[right] = elem;
        right = inc(right);
    }

    protected Object elementImpl() {
        return elements[left];
    }

    protected void dequeueImpl() {
        elements[left] = null;
        left = inc(left);
        ensureCapacity(size - 1);
    }

    protected void clearImpl() {
        ensureCapacity(1);
        left = right = 0;
    }

    protected ArrayQueue createCopy() {
        ArrayQueue result = new ArrayQueue(elements.length);
        result.left = left;
        result.right = right;
        result.size = size;
        System.arraycopy(elements, 0, result.elements, 0, elements.length);
        return result;
    }
}
