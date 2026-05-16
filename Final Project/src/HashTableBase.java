public class HashTableBase {

    int[] table;
    int size;

    public HashTableBase(int size) {
        this.size = size;
        table = new int[size];

        for (int i = 0; i < size; i++) {
            table[i] = -1;
        }
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(i + " -> ");

            if (table[i] != -1)
                System.out.print(table[i]);
            else
                System.out.print("null");

            System.out.println();
        }
    }
}