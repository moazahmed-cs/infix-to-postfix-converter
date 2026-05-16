class QNode<T> {
    T data;
    QNode<T> next;

    QNode(T data) {
        this.data = data;
        this.next = null;
    }

    public QNode() {
    }
}

public class Queue<T> {
    QNode<T> front, rear;

    public Queue() {
        front = rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void enQueue(T x) {
        QNode<T> n = new QNode<>(x);

        if (rear == null) {
            front = rear = n;
        } else {
            rear.next = n;
            rear = n;
        }
    }

    public T deQueue() {
        if (isEmpty())
            return null;

        T d = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return d;
    }

    public T peek() {
        if (isEmpty())
            return null;
        return front.data;
    }
}