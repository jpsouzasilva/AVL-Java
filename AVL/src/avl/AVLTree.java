package avl;

public class AVLTree extends BinarySearchTree {
    public static Node root;
    protected static int CURRENT_OPERATION;
    
    public AVLTree() {
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
        
        // node was found
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
                parent.balancingFactor -= 1;
            } else {
                parent.right = null;
                parent.balancingFactor += 1;
            }
        }
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
                root.balancingFactor = 0;
            } else if (isLeftChild) {
                // check if current node is left child
                parent.left = current.left;
                parent.balancingFactor += current.left.balancingFactor;
            } else {
                parent.right = current.left;
                parent.balancingFactor -= current.left.balancingFactor;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
                root.balancingFactor = 0;
            } else if (isLeftChild) {
                // check if current node is left child
                parent.left = current.right;
                parent.balancingFactor += current.right.balancingFactor;
            } else {
                parent.right = current.right;
                parent.balancingFactor -= current.right.balancingFactor;
            }
        } else if (current.left != null && current.right != null) {
            Node successor = getSuccessor(current);
            int temp = current.balancingFactor;
            if (current == root) {
                root = successor;
                root.balancingFactor = temp;
                return;
            } else if (isLeftChild) {
                parent.left = successor;
                parent.balancingFactor += successor.balancingFactor;
            } else {
                parent.right = successor;
                parent.balancingFactor -= successor.balancingFactor;
            }
            successor.parent = parent;
            successor.left = current.left;
            successor.balancingFactor += successor.left.balancingFactor;
        }
        checkBalancingFactorDelete (parent);
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
                    parent.balancingFactor += 1;
                    checkBalancingFactorInsert(parent);
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    newNode.parent = parent;
                    parent.balancingFactor -= 1;
                    checkBalancingFactorInsert(parent);
                    return;
                }
            }
        }
    }
    
    // Rotação Simples a Direita(RSD)
    private static Node rotateRight( Node oldRoot )
    {
        Node newRoot = oldRoot.left;
        System.out.println("pai: " + newRoot.data + ":" + newRoot.balancingFactor);
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
        
        System.out.println("pai: " + newRoot.data + ":" + newRoot.balancingFactor + 
                " filho esquerdo:" + newRoot.left.data + ":" + newRoot.left.balancingFactor 
                + " filho direito:" + newRoot.right.data + ":" + newRoot.right.balancingFactor);
        oldRoot.balancingFactor -= (1 + Integer.max(newRoot.balancingFactor, 0));
        newRoot.balancingFactor -= (1 + Integer.min(oldRoot.balancingFactor, 0));
        System.out.println("pai: " + newRoot.balancingFactor + " filho esquerdo:" + newRoot.left.balancingFactor + " filho direito:" + newRoot.right.balancingFactor);
        return newRoot;
    }

    // Rotação Simples a Esquerda(RSE)
    private static Node rotateLeft( Node oldRoot )
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
        
        oldRoot.balancingFactor -= (1 + Integer.max(newRoot.balancingFactor, 0));
        newRoot.balancingFactor -= (1 - Integer.min(oldRoot.balancingFactor, 0));
        return newRoot;
    }

    // Rotação Dupla a Direita(RDD)
    private static void rotateLeftRight( Node oldRoot )
    {
        oldRoot.left = rotateLeft( oldRoot.left );
        rotateRight( oldRoot );
    }

    // Rotação Dupla a Esquerda(RDE)
    private static void rotateRightLeft( Node oldRoot )
    {
        oldRoot.right = rotateRight( oldRoot.right );
        rotateLeft( oldRoot );
    }

    public static void checkBalancingFactorInsert ( Node node ) {
        // get balancing factor if children aren't null
        int leftChildBalancingFactor = (node.left != null) ? Math.abs(node.left.balancingFactor) : 0;
        int rightChildBalancingFactor = (node.right != null) ? Math.abs(node.right.balancingFactor) : 0;
        
        // attempt to know who is unbalancing the tree
        Node tempNode = (leftChildBalancingFactor - rightChildBalancingFactor) > 0 
            ? node.left : node.right;
        
        // check for unbalanced state
        if (node.balancingFactor == Math.abs(2)) {
            if (tempNode == node.left) {
                if (node.balancingFactor >= 0 && tempNode.balancingFactor >= 0) {
                    rotateRight(node);
                } else if ((node.balancingFactor >= 0 && tempNode.balancingFactor < 0)) {
                    rotateLeftRight(node);
                }
            } else if (tempNode == node.right) {
                if (node.balancingFactor < 0 && tempNode.balancingFactor <= 0) {
                    rotateLeft(node);
                } else if ((node.balancingFactor < 0 && tempNode.balancingFactor > 0)) {
                    rotateRightLeft(node);
                }
            }
        }
        if (node.parent != null) {
            if (isLeftChild(node) && tempNode != null) node.parent.balancingFactor +=1; else node.parent.balancingFactor -= 1;
            if (node.parent.balancingFactor != 0) {
                checkBalancingFactorInsert (node.parent);
            }
        }
    }
    
    public static void checkBalancingFactorDelete ( Node node ) {
        // get balancing factor if children aren't null
        int leftChildBalancingFactor = (node.left != null) ? Math.abs(node.left.balancingFactor) : 0;
        int rightChildBalancingFactor = (node.right != null) ? Math.abs(node.right.balancingFactor) : 0;
        
        // update the node's balancing factor based on children
        if (node.left != null) node.balancingFactor += leftChildBalancingFactor;
        if (node.right != null) node.balancingFactor -= rightChildBalancingFactor;
        
        // check for unbalanced state
        if (node.balancingFactor == Math.abs(2)) {
            // attempt to know who is unbalancing the tree
            Node tempNode = (leftChildBalancingFactor - rightChildBalancingFactor) > 0 
                    ? node.left : node.right;
            if (tempNode == node.left) {
                if (node.balancingFactor >= 0 && tempNode.balancingFactor >= 0) {
                    rotateRight(node.left);
                } else if ((node.balancingFactor >= 0 && tempNode.balancingFactor < 0)) {
                    rotateLeftRight(node);
                }
            } else if (tempNode == node.right) {
                if (node.balancingFactor < 0 && tempNode.balancingFactor <= 0) {
                    rotateLeft(node.right);
                } else if ((node.balancingFactor < 0 && tempNode.balancingFactor > 0)) {
                    rotateRightLeft(node);
                }
            }
        }
        if (node.parent != null && !(node.parent.balancingFactor != 0)) {
            checkBalancingFactorDelete (node.parent);
        }
    }
    
    public static void displayTree( Node root ) {
        if (root != null) {
            displayTree(root.left);
            System.out.print(" " + root.data + "(" + root.balancingFactor + ")");
            displayTree(root.right);
        }
    }
    
    public static boolean isLeaf(Node node) {
        return (node.left == null && node.right == null);
    }
    
    public static boolean isLeftChild( Node node ) {
        return (node.parent.left == node);
    }

    // NewBal(A) = OldBal(A) + 1 - min(OldBal(B), 0)
    // NewBal(B) = OldBal(B) + 1 + max(NewBal(A), 0)
    
        /*
    private static void ascendLeftSuccessor (Node root, Node successor) {
        successor.parent = root.parent;
        successor.right = turnIntoLeaf(root);
        successor.left = turnIntoLeaf(root.right);
        successor.left.parent = successor;
        successor.right.parent = successor;
        
        successor.balancingFactor = 0;
        successor.right.balancingFactor = 0;
        successor.left.balancingFactor = 0;
    }
    
    private static void ascendRightSuccessor (Node root, Node successor) {
        successor.parent = root.parent;
        successor.left = turnIntoLeaf(root);
        successor.right = turnIntoLeaf(root.left);
        successor.left.parent = successor;
        successor.right.parent = successor;
        
        successor.balancingFactor = 0;
        successor.right.balancingFactor = 0;
        successor.left.balancingFactor = 0;
    
    public static Node turnIntoLeaf (Node node) {
        node.left = null;
        node.right = null;
        return node;
    }
    }*/
}
