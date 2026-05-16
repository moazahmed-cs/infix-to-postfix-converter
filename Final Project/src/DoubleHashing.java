public class DoubleHashing extends HashTableBase {

    public DoubleHashing(int size) {
        super(size);
    }

    private int hash1(int key) {
        return Math.abs(key) % size;
    }

    private int hash2(int key) {
        return 1 + (Math.abs(key) % (size - 1));
    }

    public void insert(int key) {

        int index = hash1(key);
        int step = hash2(key);
        int i = 0;

        while (table[(index + i * step) % size] != -1) {
            i++;
        }

        table[(index + i * step) % size] = key;
    }
}