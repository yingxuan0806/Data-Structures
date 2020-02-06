import java.util.Iterator;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* Instance variables here. */
    private Entry[] arr;
    private int size;
    private double loadfactor;

    /* Creates a new hash map with a default array of size 16 and
    a maximum load factor of 0.75. */
    public HashMap() {
        arr = new Entry[16];
        size = 0;
        loadfactor = 0.75;
    }

    /* Creates a new hash map with an array of size INITIALCAPACITY and
    a maximum load factor of 0.75. */
    HashMap(int initialCapacity) {
        arr = new Entry[initialCapacity];
        size = 0;
        loadfactor = 0.75;
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    HashMap(int initialCapacity, double loadFactor) {
        arr = new Entry[initialCapacity];
        size = 0;
        loadfactor = loadFactor;
    }

    /* Returns the length of this HashMap's internal array. */
    public int capacity() {
        return arr.length;
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        Entry currEntry = arr[index];
        while (currEntry != null) {
            if (currEntry.key == key) {
                return true;
            }
            currEntry = currEntry.next;
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        Entry currEntry = arr[index];
        while (currEntry != null) {
            if (currEntry.key == key) {
                return (V) currEntry.value;
            }
            currEntry = currEntry.next;
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        Entry currEntry = arr[index];
        // Case where the key already exists in the hashTable; Change the value
        if (containsKey(key)) {
            while (currEntry != null) {
                if (currEntry.key == key) {
                    currEntry.value = value;
                    return;
                }
                currEntry = currEntry.next;
            }
        }
        // If adding the new key-value pair exceeds the max load factor,
        // resize hashTable array length
        double factor = ((double) size + 1) / (double) arr.length;
        if (factor > loadfactor) {
            resize();
        }
        index = Math.floorMod(key.hashCode(), arr.length);
        // Case where there are no items in the hashTable[index] yet
        if (currEntry == null) {
            arr[index] = new Entry(key, value);
            size += 1;
            return;
        }
        // Else
        while (currEntry.next != null) {
            currEntry = currEntry.next;
        }
        currEntry.next = new Entry(key, value);
        size += 1;
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public V remove(K key) {
        if (containsKey(key)) {
            int index = Math.floorMod(key.hashCode(), arr.length);
            if (arr[index].key == key) {
                V returnValue = (V) arr[index].value;
                arr[index] = null;
                size -= 1;
                return returnValue;
            }
            Entry currEntry = arr[index];
            while (currEntry != null) {
                if (currEntry.next.key == key) {
                    V returnValue = (V) currEntry.next.value;
                    currEntry.next = null;
                    size -= 1;
                    return returnValue;
                }
                currEntry = currEntry.next;
            }
        }
        return null;
    }

    public void resize() {
        int newArrayLength = arr.length * 2;
        Entry[] newHashTable = new Entry[newArrayLength];
        int index = 0;
        while (index != arr.length) {
            Entry currEntry = arr[index];
            while (currEntry != null) {

                int newIndex = Math.floorMod(currEntry.key.hashCode(), arr.length);
                Entry newCurrEntry = newHashTable[newIndex];
                if (newCurrEntry == null) {
                    newHashTable[newIndex] = new Entry(currEntry.key, currEntry.value);
                } else {
                    while (newCurrEntry.next != null) {
                        newCurrEntry = newCurrEntry.next;
                    }
                    newCurrEntry.next = new Entry(currEntry.key, currEntry.value);
                }

                currEntry = currEntry.next;
            }
            index += 1;
        }
        arr = newHashTable;
    }

    public void clear() {
        int index = 0;
        while (index != arr.length) {
            if (arr[index] != null) {
                arr[index] = null;
            }
            index += 1;
        }
        size = 0;
    }

    public boolean remove(K key, V value) {
        if (remove(key) != null) {
            return true;
        }
        return false;
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private static class Entry<K, V> {

        private K key;
        private V value;
        private Entry next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        Entry(K key, V value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
