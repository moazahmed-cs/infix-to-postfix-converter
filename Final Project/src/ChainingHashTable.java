class Node {
    String data;
    Node next;

    Node(String data) {
        this.data = data;
    }
}

public class ChainingHashTable {

    Node[] table;
    int size;

    public ChainingHashTable(int size) {
        this.size = size;
        table = new Node[size];
    }

    public int hash(String key) {
        return Math.abs(key.hashCode() % size);
    }

    public void insert(int key) {

        int index = hash(String.valueOf(key));

        Node newNode = new Node(String.valueOf(key));

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node temp = table[index];

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode;
        }
    }


    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(i + " -> ");

            Node temp = table[i]; // linked list head

            while (temp != null) {
                System.out.print(temp.data + " -> ");
                temp = temp.next;
            }

            System.out.println("null");
        }
    }

}