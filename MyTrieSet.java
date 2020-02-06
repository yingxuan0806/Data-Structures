import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.lang.Exception;


public class MyTrieSet implements TrieSet61BL {

    private Node root;


    public MyTrieSet() {
        root = new Node(false);
    }

    @Override
    /** Clears all items out of Trie */
    public void clear() {
        root = new Node(false);
    }

    @Override
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            throw new IllegalArgumentException();
        }
        Node node = find(key);
        return node != null && node.isKey;
    }

    @Override
    /** Inserts string KEY into Trie */
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node current = root;
        for (int i = 0, n = key.length(); i < n; i += 1) {
            char c = key.charAt(i);
            if (!current.map.containsKey(c)) {
                current.map.put(c, new Node(false));
            }
            current = current.map.get(c);
        }
        current.isKey = true;
    }


    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        Node node = find(prefix);
        collect(prefix, keys, node);
        return keys;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 18. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) throws UnsupportedOperationException {
        return null;
    }

    private Node find(String key) {
        Node current = root;
        for (int i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            if (!current.map.containsKey(c)) {
                return null;
            }
            current = current.map.get(c);
        }
        return current;
    }

    private void collect(String s, List<String> x, Node node) {
        if (node == null) {
            return;
        }
        if (node.isKey) {
            x.add(s);
        }
        for (char c : node.map.keySet()) {
            collect(s + c, x, node.map.get(c));
        }
    }



    private class Node {
        private HashMap<Character, Node> map;
        private boolean isKey;

        private Node(boolean bool) {
            this.isKey = bool;
            map = new HashMap<>();
        }

    }
}