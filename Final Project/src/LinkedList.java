class Node<T> {

    public T data;
    public Node<T> next;
}

public class LinkedList<T> {

    Node<T> head = null;

    public int count() {
        int c = 0;
        Node<T> temp = head;
        while (temp != null) {
            c++;
            temp = temp.next;
        }
        return c;
    }

    static <T> Node<T> CreateNode(T x) {
        Node<T> n = new Node<>();
        if (n != null) {
            n.data = x;
            n.next = null;
        }
        return n;
    }

    void AddNode(T x) {
        Node<T> temp;
        Node<T> n = CreateNode(x);

        if (n != null) {
            if (head == null) {
                head = n;
            } else {
                temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = n;
            }
        }
    }
}