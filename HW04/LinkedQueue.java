
public class LinkedQueue extends AbstractQueue{
    private Node head;
    private Node tail;

    protected void enqueueImpl(Object elem) {
        if (size == 0){
            head = tail = new Node(null, elem);
        } else {
            tail.next = new Node(null, elem);
            tail = tail.next;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void dequeueImpl() {
        head = head.next;
        if (size == 1) {
            tail = null;
        }
    }

    protected void clearImpl() {
        head = tail = null;
    }

    protected LinkedQueue createCopy() {
        LinkedQueue result = new LinkedQueue();
        Node temp = head;
        while (temp != null) {
            result.enqueue(temp.value);
            temp = temp.next;
        }
        return result;
    }

    private class Node {
        private Node next;
        private Object value;
        private Node(Node newNext, Object newValue) {
            next = newNext;
            value = newValue;
        }
    }
}
