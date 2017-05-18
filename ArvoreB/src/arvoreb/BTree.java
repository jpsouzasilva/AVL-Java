/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreb;

import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author joaopaulo
 */

public class BTree {
    public Node root;
    public final int order;
    public final int LEFT = 0;
    public final int RIGHT = 1;
    
    public BTree(int order) {
        this.order = order;
    }
    
    public Node find(Character keyToFind) {
        Node current = root;
        int level = 0;
        boolean foundNode = false;
        while (current!=null && !foundNode) {
            for (Character key : current.keys) {
                if (Objects.equals(keyToFind, key)) {
                    foundNode = true;
                    break;
                } else if (keyToFind <= key) {
                    current = current.children.get(key)[LEFT];
                    level++;
                    break;
                } else if (keyToFind >= key) {
                    if (key.equals(current.keys.last())) {
                        if (current.children.get(key) != null) {
                            current = current.children.get(key)[RIGHT];
                            level++;
                        } else {
                            current = null;
                        }
                        break;
                    }
                }
            }
        }
        if (current == null) {
            System.out.println("Node not found.");
        } else {
            System.out.println("Node found at level: " + level);
        }
        return current;
    }
    
    public Node findInsertPosition (Character keyToInsert) throws Exception {
        Node current = root;
        boolean keyInserted = false;
        while (current!=null && !keyInserted) {
            for (Character key : current.keys) {
                if (keyToInsert <= key) {
                    if (current.children.get(key)!=null && current.children.get(key)[LEFT]!= null) {
                        current = current.children.get(key)[LEFT];
                        break;
                    } else {
                        current.keys.add(keyToInsert);
                        keyInserted = true;
                        break;
                    }
                } else if (keyToInsert >= key) {
                    if (current.children.get(key)!=null && current.children.get(key)[RIGHT] != null) {
                        current = current.children.get(key)[RIGHT];
                        break;
                    } else {
                        current.keys.add(keyToInsert);
                        keyInserted = true;
                        break;
                    }
                }
            }
        }
        return current;
    }
    
    public void tryFixRecusiverly (Node node) throws Exception {
        if (node == null) {
            return;
        }
        if (node.isSizeOverflowed()) {
            Character keyToAscend = (Character) node.keys.toArray()[node.keys.size()/2];
            if (node.parent == null) {
                // root
                root = new Node(this.order, Arrays.asList(keyToAscend));
                node.keys.remove(keyToAscend);
                node.parent = root;
            }
            TreeSet<Character> leftSide = new TreeSet<>();
            TreeSet<Character> rightSide = new TreeSet<>();
            for (Character key : node.keys) {
                if (key < keyToAscend) {
                    leftSide.add(key);
                } else {
                    rightSide.add(key);
                }
            }
            node.parent.children.put(keyToAscend, new Node[] {new Node(this.order, leftSide, node.parent), new Node(this.order, rightSide, node.parent)} );
            tryFixRecusiverly(node.parent);
        }
    }
    
    public void insert(Character key) throws Exception {
        if (root == null) {
            root = new Node(this.order, Arrays.asList(key));
            System.out.println("Root initialized.");
        } else {
            Node nodeInsertedInto = findInsertPosition(key);
            tryFixRecusiverly(nodeInsertedInto);
            System.out.println("Node inserted successfully.");
        }
    }
}
