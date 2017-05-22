package arvorerubronegra;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackRedTree extends BinarySearchTree {
    public Node root;
    public Map<Integer, List<Node>> treeLevels;
    public final int DOUBLE_BLACK = -1;
    public final int BLACK = 0;
    public final int RED = 1;
    
    public BlackRedTree() {
        this.root = null;
    }

    @Override
    public void insert(int id) {
        // start node as red
        Node newNode = new Node(id, 1);
        if (root == null) {
            root = newNode;
            checkColor(newNode);
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (id < current.data) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    newNode.parent = parent;
                    checkColor(newNode);
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    newNode.parent = parent;
                    checkColor(newNode);
                    return;
                }
            }
        }
    }
    
    @Override
    public void delete(int id) {
        Node parent = root;
        Node current = root;
        Node successor = null;
        boolean isLeftChild = false;
        while (current.data != id) {
            parent = current;
            if (current.data > id) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null) {
                return;
            }
        }
        if (current.color == BLACK) current.color = DOUBLE_BLACK;
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild == true) {
                if (current.color == DOUBLE_BLACK) {
                    checkDoubleBlack(current);
                } else parent.left = null;
            } else {
                if (current.color == DOUBLE_BLACK) {
                    checkDoubleBlack(current);
                } else parent.right = null;
            }
            return;
        }
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
                root.parent = null;
                root.color = BLACK;
            } else if (isLeftChild) {
                parent.left = current.left;
                successor = parent.left;
            } else {
                parent.right = current.left;
                successor = parent.right;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
                root.parent = null;
                root.color = BLACK;
            } else if (isLeftChild) {
                parent.left = current.right;
                successor = parent.left;
            } else {
                parent.right = current.right;
                successor = parent.right;
            }
        } else if (current.left != null && current.right != null) {
            //now we have found the minimum element in the right sub tree
            successor = getSuccessor(current);
            if (current == root) {
                root = successor;
                if (successor.color == BLACK) {
                    // CASO 3: Ne -> Ne
                    Node doubleBlackNode = new Node(0);
                    if (successor.parent.left == null) {
                        successor.parent.left = doubleBlackNode;
                    } else if (successor.parent.right == null) {
                        successor.parent.right = doubleBlackNode;
                    } else {
                        successor.parent.right.color = BLACK;
                        successor.parent.left.color = BLACK;
                    }
                    doubleBlackNode.color = DOUBLE_BLACK;
                    doubleBlackNode.parent = successor.parent;
                    successor.left = current.left;
                    if (successor.left != null) successor.left.parent = successor;
                    successor.right = current.right;
                    if (successor.right != null) successor.right.parent = successor;
                    root.parent = null;
                    root.color = BLACK;
                    checkDoubleBlack(doubleBlackNode);
                } else {
                    // CASO 2: Ne -> Ru
                    root.parent = null;
                    root.color = BLACK;
                }
                return;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
            if (successor.left != null) successor.left.parent = successor;
            successor.right = current.right;
            if (successor.right != null) successor.right.parent = successor;
        }
       
        if (current.color == DOUBLE_BLACK && successor != null && successor.color == RED) {
             // CASO 2: Ne -> Ru
            successor.color = BLACK;
            successor.parent = current.parent;
            
        } else if (successor != null && successor.color == BLACK) {
            // CASO 4: Ru -> Ne
            if (current.color == RED) successor.color = RED;
            
            // CASO 3: Ne -> Ne
            Node doubleBlackNode = new Node(0);
            if (successor.parent.left == null) {
                successor.parent.left = doubleBlackNode;
            } else if (successor.parent.right == null) {
                successor.parent.right = doubleBlackNode;
            } else {
                successor.parent.right.color = BLACK;
                successor.parent.left.color = BLACK;
            }
            doubleBlackNode.color = DOUBLE_BLACK;
            doubleBlackNode.parent = successor.parent;
            successor.left = current.left;
            if (successor.left != null) successor.left.parent = successor;
            successor.right = current.right;
            if (successor.right != null) successor.right.parent = successor;
            successor.parent = current.parent;
            checkDoubleBlack(doubleBlackNode);
            
        }
        // CASO 1: Ru -> Ru
        
    }
    
    private void checkDoubleBlack (Node current) {
        Node parent = current.parent;
        boolean isLeftChild = isLeftChild(current);
        if (isLeftChild) {
            if (hasTwoChildren(parent) && parent.color == BLACK && parent.right.color == RED) {
                // 3 CASO 1
                parent.right.color = BLACK;
                parent.color = RED;
                rotateLeft(parent);
                checkDoubleBlack(current);
            } else if (hasTwoChildren(parent) && parent.right.color == BLACK && hasTwoChildren(parent.right) &&
            parent.right.left.color == BLACK && parent.right.right.color == BLACK) {
                // 3 CASO 2A
                current.color = BLACK;
                parent.right.color = RED;
                parent.color = DOUBLE_BLACK;
                checkDoubleBlack(parent);
            } else if (hasTwoChildren(parent) && parent.color == RED && parent.right.color == BLACK && 
            ((hasTwoChildren(parent.right) && parent.right.left.color == BLACK && parent.right.right.color == BLACK ) || hasNoChildren(parent.right))) {
                // 3 CASO 2B - TERMINAL
                parent.color = BLACK;
                parent.right.color = RED;
                parent.left = null;
            } else if (hasTwoChildren(parent) && parent.right.color == BLACK && ((hasTwoChildren(parent.right) &&
            parent.right.left.color == RED && parent.right.right.color == BLACK)) || hasLeftChild(parent.right) && parent.right.left.color == RED) {
                // 3 CASO 3
                parent.right.left.color = BLACK;
                parent.right.color = RED;
                parent.left = null;
                rotateRight(parent.right);
            } else if (hasTwoChildren(parent) && parent.right.color == BLACK && parent.right.right != null && 
            parent.right.right.color == RED) {
                // 3 CASO 4
                parent.right.color = parent.color;
                parent.right.right.color = BLACK;
                parent.color = BLACK;
                parent.left = null;
                rotateLeft(parent);
            }
        } else {
            if (hasTwoChildren(parent) && parent.left.color == RED) {
                // 3 CASO 1
                parent.left.color = BLACK;
                parent.color = RED;
                rotateRight(parent);
                checkDoubleBlack(current);
            } else if (hasTwoChildren(parent) && parent.left.color == BLACK && hasTwoChildren(parent.left) &&
            parent.left.right.color == BLACK && parent.left.left.color == BLACK) {
                // 3 CASO 2A
                current.color = BLACK;
                parent.left.color = RED;
                parent.color = DOUBLE_BLACK;
                checkDoubleBlack(parent);
            } else if (hasTwoChildren(parent) && parent.color == RED && parent.left.color == BLACK && 
            ((hasTwoChildren(parent.left) && parent.left.left.color == BLACK && parent.left.right.color == BLACK ) || hasNoChildren(parent.left))) {
                // 3 CASO 2B - TERMINAL
                parent.color = BLACK;
                parent.left.color = RED;
                parent.right = null;
            } else if (hasTwoChildren(parent) && parent.left.color == BLACK && ((hasTwoChildren(parent.left) &&
            parent.left.right.color == RED && parent.left.left.color == BLACK) || hasRightChild(parent.left) && parent.left.right.color == RED)) {
                // 3 CASO 3
                parent.left.right.color = BLACK;
                parent.left.color = RED;
                parent.right = null;
                rotateLeft(parent.right);
            } else if (hasTwoChildren(parent) && parent.left.color == BLACK && parent.left.left != null && 
            parent.left.left.color == RED) {
                // 3 CASO 4
                parent.left.color = parent.color;
                parent.left.left.color = BLACK;
                parent.color = BLACK;
                parent.right = parent.right.right;
                rotateRight(parent);
            }
        }
    }
    
    // Rotação Simples a Direita(RSD)
    private Node rotateRight( Node oldRoot )
    {
        Node newRoot = oldRoot.left;
        if (oldRoot.parent != null) {
            if (isLeftChild(oldRoot)) oldRoot.parent.left = newRoot; else oldRoot.parent.right = newRoot ;
        } else {
            root = newRoot;
        }
        newRoot.parent = oldRoot.parent;
        
        oldRoot.left = newRoot.right;
        if (oldRoot.left != null) oldRoot.left.parent = oldRoot;
        
        newRoot.right = oldRoot;
        newRoot.right.parent = newRoot;
        
        return newRoot;
    }

    // Rotação Simples a Esquerda(RSE)
    private Node rotateLeft( Node oldRoot )
    {
        Node newRoot = oldRoot.right;
        if (oldRoot.parent != null) {
            if (isLeftChild(oldRoot)) oldRoot.parent.left = newRoot; else oldRoot.parent.right = newRoot ;
        } else {
            root = newRoot;
        }
        newRoot.parent = oldRoot.parent;
        
        oldRoot.right = newRoot.left;
        if (oldRoot.right != null) oldRoot.right.parent = oldRoot;
        
        newRoot.left = oldRoot;
        newRoot.left.parent = newRoot;
        return newRoot;
    }

    // Rotação Dupla a Direita(RDD)
    private Node rotateLeftRight( Node oldRoot )
    {
        oldRoot.left = rotateLeft( oldRoot.left );
        return rotateRight( oldRoot );
    }

    // Rotação Dupla a Esquerda(RDE)
    private Node rotateRightLeft( Node oldRoot )
    {
        oldRoot.right = rotateRight( oldRoot.right );
        return rotateLeft( oldRoot );
    }
    
    public void checkColor(Node node) {
        while (node != null) {
            // parent has black color
            if (node.parent != null) {
                if (node.parent.color == 0 && node.color == 1) {
                    // parent is black and node is red - ok
                    break;
                } else if (node.parent.color == 1) {
                    // parent is red and node is red - not ok
                    Node grandParent = node.parent.parent;
                    if (!isLeftChild(node.parent) && (grandParent.left == null || (grandParent.left != null && grandParent.left.color == BLACK))) {
                        if (!isLeftChild(node)) {
                            grandParent.color = 1;
                            node.parent.color = 0;
                            rotateLeft(grandParent);
                        } else {
                            grandParent.color = 1;
                            node.color = 0;
                            rotateRightLeft(grandParent);
                        }
                    } else if (isLeftChild(node.parent) && (grandParent.right == null) || (grandParent.right != null && grandParent.right.color == BLACK)) {
                        if (isLeftChild(node)) {
                            grandParent.color = 1;
                            node.parent.color = 0;
                            rotateRight(grandParent);
                        } else {
                            grandParent.color = 1;
                            node.color = 0;
                            rotateLeftRight(grandParent);
                        }
                    } else if ((isLeftChild(node.parent) && grandParent.right.color == RED) || (!isLeftChild(node.parent) && grandParent.left.color == RED)) {
                        // red uncle - should push blackness down and become red
                        grandParent.left.color = 0;
                        grandParent.right.color = 0;
                        grandParent.color = 1;
                    }
                    node = grandParent;
                }
            } else {
                // root - should be painted black always
                node.color = 0;
                break;
            }
        }
    }
    
    public static void displayTree( Node root ) {
        if (root != null) {
            displayTree(root.left);
            System.out.print(" " + root.data + "(" + (root.color == 0 ? "p" : "v") + ")");
            displayTree(root.right);
        }
    }
    
    public void generateTreeView (Node root, String fileName) {
        treeLevels = new HashMap<>();
        buildTreeLevels(root, 0);
        int i = 0;
        while (treeLevels.get(i) != null) {
            if (i==0 || i+1 == treeLevels.size()) {
                i++;
                continue;
            }
            int maxNodeSize = new Double(Math.pow(2, i)).intValue();
            int j;
            for (j = 0; j < treeLevels.get(i).size(); j++) {
                Node node = (Node) treeLevels.get(i).get(j);
                if (node.left == null) {
                    treeLevels.get(i+1).add(2*j, new Node(0));
                }
                if (node.right == null) {
                    treeLevels.get(i+1).add((2*j)+1, new Node(0));
                }
            }
            while (j < maxNodeSize) {
                treeLevels.get(i+1).add(new Node(0));
                j++;
            }
            i++;
        }
        String htmlOutput = "<!doctype html> <html> <head> <script type='text/javascript' src='jquery-1.7.2.min.js'></script><script type='text/javascript' src='jqSimpleConnect.js'></script> <meta charset='utf-8'> <title>Visualização da árvore Rubro-Negra</title> <meta name='description' content='Visualização da árvore Rubro-Negra'> <meta name='author' content='20151014040004'> <style> .row { width: 100%; text-align: center; } .node { width: 30px; border-radius: 6px; border: 1px solid black; display: inline-table; margin-top: 10px; margin-bottom: 10px; } .redNode { background-color: red;  color: black; } .blackNode { background-color: black; color: white; } </style> </head> <body>";
        htmlOutput += "<div class='row'><div class='node blackNode' id='_" + root.data + "'>" + root.data + "</div></div>";
        i = 1;
        int startMagin = 80;
        while (treeLevels.get(i) != null) {
            htmlOutput += "<div class='row'>";
            int margin = new Double((startMagin/i)*1.2).intValue();
            for (Node node : treeLevels.get(i)) {
                if (node.parent != null) {
                    htmlOutput += String.format("<div id='%s' style='margin-left:%dpx; margin-right: %dpx;' class='node %s'>%d</div>", String.format("%d_%d", node.parent.data, node.data), margin, margin, node.color == 1 ? "redNode" : "blackNode", node.data);
                } else {
                    htmlOutput += String.format("<div style='margin-left:%dpx; margin-right: %dpx;' class='node'>-</div>", (startMagin/i),(startMagin/i));
                }
            }
            htmlOutput += "</div>";
            i++;
        }
        treeLevels = null;
        htmlOutput += "<script>$(\".node[id]\").each(function(q,b){ var children = $(\".node[id^=\" + b.id.split(\"_\")[1] + \"_]\"); var i = 0; for (;children[i];) { jqSimpleConnect.connect(\"#\" + b.id, \"#\" + children[i].id, {radius: 2, color: 'black', anchorA: \"vertical\", anchorB: \"vertical\"}); i++; } b.style.zIndex = \"999\"; b.style.position = \"relative\"; }); window.onresize = function () { jqSimpleConnect.repaintAll(); };</script>";
        htmlOutput += "</body></html>";
        saveToFile(htmlOutput, fileName);
    }
    
    protected void buildTreeLevels(Node root, int level) {
        if (root != null) {
            buildTreeLevels (root.left, level+1);
            if (treeLevels.get(level) == null) {
                treeLevels.put(level, new ArrayList<>());
            }
            treeLevels.get(level).add(root);
            buildTreeLevels (root.right, level+1);
        }
    }
    
    protected void saveToFile(String fileContents, String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(fileName), "utf-8"))) {
            writer.write(fileContents);
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    protected boolean isLeftChild( Node node ) {
        return (node.parent.left == node);
    }
    
    protected boolean hasNoChildren (Node node) {
        return node.left == null && node.right == null;
    }
    
    protected boolean hasTwoChildren (Node node) {
        return hasRightChild(node) && hasLeftChild(node);
    }
    
    protected boolean hasLeftChild(Node node) {
        return node.left != null;
    }
    
    protected boolean hasRightChild(Node node) {
        return node.right != null;
    }
}
