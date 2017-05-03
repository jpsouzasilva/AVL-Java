package arvorerubronegra;

public class BlackRedTree extends BinarySearchTree {
    public static Node root;
    protected static int CURRENT_OPERATION;
    
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
                    if (grandParent != null) {
                        if (!isLeftChild(node) && grandParent.color == 0 && grandParent.left == null) {
                            grandParent.color = 1;
                            grandParent.right.color = 0;
                            rotateLeft(grandParent);
                        } else  if (isLeftChild(node) && grandParent.color == 0 && grandParent.right == null) {
                            grandParent.color = 1;
                            grandParent.left.color = 0;
                            rotateRight(grandParent);
                        } else if ((isLeftChild(node.parent) && grandParent.right.color == 1) ||
                           (!isLeftChild(node.parent) && grandParent.left.color == 1)) {
                            // red uncle - should push blackness down and become red
                            grandParent.left.color = 0;
                            grandParent.right.color = 0;
                            grandParent.color = 1;
                        }
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
    
    public boolean isLeaf(Node node) {
        return (node.left == null && node.right == null);
    }
    
    public boolean isLeftChild( Node node ) {
        return (node.parent.left == node);
    }
}
