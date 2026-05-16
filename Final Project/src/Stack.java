class StackNode<T> {

    T data;
    StackNode<T> next;

    public StackNode(T x) {
        this.data = x;
        this.next = null;
    }
}

public class Stack<T> {

    private StackNode<T> top;

    public Stack() {
        this.top = null;
    }

    public void push(T x) { // insert at head
        StackNode<T> n = new StackNode<>(x);
        n.next = top;
        top = n;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T x = top.data;
        top = top.next;
        return x;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    public int size() {
        StackNode<T> temp = top;
        int count = 0;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }
}