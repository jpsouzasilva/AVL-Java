package arvoreb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


public class Node {
    TreeSet<Character> keys = new TreeSet<>();
    Map<Character, Node[]> children = new HashMap<>();
    Node parent;
    private final int minKeys, maxKeys;
    
    // cision default constructor
    public Node(int order, TreeSet<Character> keys, Node parent) throws Exception {
        minKeys = order-1;
        if (keys.size() < minKeys) {
            throw new Exception();
        }
        this.keys = keys;
        maxKeys = (2*order)-1;
        this.parent = parent;
    }
    
    // root default constructor
    public Node(int order, List<Character> keys) throws Exception {
        minKeys = 1;
        if (keys.size() < minKeys) {
            throw new Exception();
        }
        for (Character key : keys) {
            this.keys.add(key);
        }
        maxKeys = (2*order)-1;
    }
    
    public boolean isSizeOverflowed() {
        return keys.size() >= maxKeys;
    }
}
