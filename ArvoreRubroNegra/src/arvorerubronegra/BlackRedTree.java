package arvorerubronegra;

public class BlackRedTree extends BinarySearchTree {
    public static Node root;
    protected static int CURRENT_OPERATION;
    
    public BlackRedTree() {
        this.root = null;
    }

    @Override
    public void delete(int id) {
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;
        
        // trying to find the node
        while (current.data != id) {
            parent = current;
            if (current.data > id) {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current == null) {
                return;
            }
        }
        
        // node was found
        isLeftChild = isLeftChild(current);
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                // check if current node is left child
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.left != null && current.right != null) {
            Node[] successorAndParent = getSuccessorAndSuccessorParent(current);
            if (current == root) {
                root = successorAndParent[0];
                root.left = current.left;
                root.right = current.right;
                return;
            } else if (isLeftChild) {
                parent.left = successorAndParent[0];
            } else {
                parent.right = successorAndParent[0];
            }
            successorAndParent[0].parent = parent;
            successorAndParent[0].left = current.left;
            successorAndParent[0].right = current.right;
            
            // color balancing on sucessor's parent
            parent = successorAndParent[1];
        }
        checkColor(parent);
    }

    @Override
    public void insert(int id) {
        Node newNode = new Node(id);
        if (root == null) {
            root = newNode;
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
    
    public Node checkColor(Node node) {
        return null;
    }
        
    public Node[] getSuccessorAndSuccessorParent (Node deleteNode) {
        Node successor = null;
        Node successorParent = null;
        Node current = deleteNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //check if successor has the right child, it cannot have left child for sure
        //if it does have the right child, add it to the left of successorParent.
        if (successor != deleteNode.right) {
            successorParent.left = successor.right;
            successor.right = deleteNode.right;
        }
        return new Node[] {successor, successorParent};
    }
    
    public static void displayTree( Node root ) {
        if (root != null) {
            displayTree(root.left);
            System.out.print(" " + root.data + "(" + root.cor + ")");
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
