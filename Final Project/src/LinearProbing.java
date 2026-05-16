public class LinearProbing extends HashTableBase {

    public LinearProbing(int size) {
        super(size);
    }

    private int hash(int key) {
        return Math.abs(key) % size;
    }

    public void insert(int key) {

        int index = hash(key);

        while (table[index] != -1) {
            index = (index + 1) % size;
        }

        table[index] = key;
    }
}