public class QuadraticProbing extends HashTableBase {

    public QuadraticProbing(int size) {
        super(size);
    }

    private int hash(int key) {
        return Math.abs(key) % size;
    }

    public void insert(int key) {

        int index = hash(key);
        int i = 0;

        while (table[(index + i * i) % size] != -1) {
            i++;
        }

        table[(index + i * i) % size] = key;
    }
}