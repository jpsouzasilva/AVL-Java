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
    public static Node root;
    public static Map<Integer, List<Node>> treeLevels;
    
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
//                    if (grandParent != null) {
                    if (!isLeftChild(node) && grandParent.left == null) {
                        if (!isLeftChild(node.parent)) {
                            grandParent.color = 1;
                            grandParent.right.color = 0;
                            rotateLeft(grandParent);
                        } else {
                            grandParent.color = 1;
                            node.color = 0;
                            rotateLeftRight(grandParent);
                        }
                    } else if (isLeftChild(node) && grandParent.right == null) {
                        if (isLeftChild(node.parent)) {
                            grandParent.color = 1;
                            grandParent.left.color = 0;
                            rotateRight(grandParent);
                        } else {
                            grandParent.color = 1;
                            node.color = 0;
                            rotateRightLeft(grandParent);
                        }
                    } else if ((isLeftChild(node.parent) && grandParent.right.color == 1) ||
                       (!isLeftChild(node.parent) && grandParent.left.color == 1)) {
                        // red uncle - should push blackness down and become red
                        grandParent.left.color = 0;
                        grandParent.right.color = 0;
                        grandParent.color = 1;
                    }
//                    }
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
    
    public void generateTreeView (Node root) {
        treeLevels = new HashMap<>();
        buildTreeLevels(root, 0);
        String htmlOutput = "<!doctype html> <html> <head> <script type='text/javascript' src='jquery-1.7.2.min.js'></script><script type='text/javascript' src='jqSimpleConnect.js'></script> <meta charset='utf-8'> <title>Visualização da árvore Rubro-Negra</title> <meta name='description' content='Visualização da árvore Rubro-Negra'> <meta name='author' content='20151014040004'> <style> .row { width: 100%; text-align: center; } .node { width: 30px; border-radius: 6px; border: 1px solid black; display: inline-table; margin-top: 10px; margin-bottom: 10px; } .redNode { background-color: red;  color: black; } .blackNode { background-color: black; color: white; } </style> </head> <body>";
        htmlOutput += "<div class='row'><div class='node blackNode' id='_" + root.data + "'>" + root.data + "</div></div>";
        int i = 1;
        int startMagin = 80;
        while (treeLevels.get(i) != null) {
            htmlOutput += "<div class='row'>";
            int k = 0;
            for (int j = 0; j < (Math.pow(2, i)); j++) {
                int margin = new Double((startMagin/i)*1.2).intValue();
                if (k < treeLevels.get(i).size() && ((Node)treeLevels.get(i).get(k)).parent.equals((Node)treeLevels.get(i-1).get(j/2))) {
                    htmlOutput += String.format("<div id='%s' style='margin-left:%dpx; margin-right: %dpx;' class='node %s'>%d</div>", String.format("%d_%d", ((Node)treeLevels.get(i-1).get(j/2)).data, ((Node)treeLevels.get(i).get(k)).data), margin, margin,(((Node)treeLevels.get(i).get(k)).color == 0 ? "blackNode" : "redNode"), ((Node)treeLevels.get(i).get(k)).data);
                    k++;
                } else {
                    htmlOutput += String.format("<div style='margin-left:%dpx; margin-right: %dpx;' class='node'>-</div>", (startMagin/i),(startMagin/i));
                }
            }
            htmlOutput += "</div>";
            i++;
        }
        htmlOutput += "<script>$(\".node[id]\").each(function(q,b){ var children = $(\".node[id^=\" + b.id.split(\"_\")[1] + \"]\"); var i = 0; for (;children[i];) { jqSimpleConnect.connect(\"#\" + b.id, \"#\" + children[i].id, {radius: 2, color: 'black', anchorA: \"vertical\", anchorB: \"vertical\"}); i++; } b.style.zIndex = \"999\"; b.style.position = \"relative\"; }); window.onresize = function () { jqSimpleConnect.repaintAll(); };</script>";
        htmlOutput += "</body></html>";
        saveToFile(htmlOutput);
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
    
    protected void saveToFile(String fileContents) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("avlTree.html"), "utf-8"))) {
            writer.write(fileContents);
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean isLeftChild( Node node ) {
        return (node.parent.left == node);
    }
}
