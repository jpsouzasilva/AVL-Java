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
                parent.balancingFactor -= 1;
            } else {
                parent.right = null;
                parent.balancingFactor += 1;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
                root.balancingFactor = 0;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
            parent.balancingFactor -= 1;
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
                root.balancingFactor = 0;
            } else if (isLeftChild) {
                // check if current node is left child
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
            parent.balancingFactor += 1;
        } else if (current.left != null && current.right != null) {
            Node[] successorAndParent = getSuccessorAndSuccessorParent(current);
            if (current == root) {
                root = successorAndParent[0];
                root.left = current.left;
                root.right = current.right;
                root.balancingFactor = current.balancingFactor;
                return;
            } else if (isLeftChild) {
                parent.left = successorAndParent[0];
            } else {
                parent.right = successorAndParent[0];
            }
            successorAndParent[0].parent = parent;
            successorAndParent[0].left = current.left;
            successorAndParent[0].right = current.right;
            successorAndParent[0].balancingFactor = current.balancingFactor;
            
            // assigning the successor's old parent to balance check
            parent = successorAndParent[0];
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
        oldRoot.balancingFactor -= (1 + Integer.max(newRoot.balancingFactor, 0));
        newRoot.balancingFactor -= (1 + Integer.min(oldRoot.balancingFactor, 0));
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
        
        oldRoot.balancingFactor += (1 - Integer.min(newRoot.balancingFactor, 0));
        newRoot.balancingFactor += (1 + Integer.max(oldRoot.balancingFactor, 0));
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

    public Node checkBalancingFactorInsert ( Node node ) {
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
                    return checkBalancingFactorInsert( rotateRight(node) );
                } else if ((node.balancingFactor >= 0 && tempNode.balancingFactor < 0)) {
                    return checkBalancingFactorInsert( rotateLeftRight(node) );
                }
            } else if (tempNode == node.right) {
                if (node.balancingFactor < 0 && tempNode.balancingFactor <= 0) {
                    return checkBalancingFactorInsert( rotateLeft(node) );
                } else if ((node.balancingFactor < 0 && tempNode.balancingFactor > 0)) {
                    return checkBalancingFactorInsert(rotateRightLeft(node));
                }
            }
        }
        if (node.parent != null) {
            if (isLeftChild(node)) node.parent.balancingFactor +=1; else node.parent.balancingFactor -= 1;
            if (node.parent.balancingFactor != 0) {
                checkBalancingFactorInsert (node.parent);
            }
        }
        return null;
    }
    
    public Node checkBalancingFactorDelete ( Node node ) {
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
                    return checkBalancingFactorInsert( rotateRight(node) );
                } else if ((node.balancingFactor >= 0 && tempNode.balancingFactor < 0)) {
                    return checkBalancingFactorInsert( rotateLeftRight(node) );
                }
            } else if (tempNode == node.right) {
                if (node.balancingFactor < 0 && tempNode.balancingFactor <= 0) {
                    return checkBalancingFactorInsert( rotateLeft(node) );
                } else if ((node.balancingFactor < 0 && tempNode.balancingFactor > 0)) {
                    return checkBalancingFactorInsert(rotateRightLeft(node));
                }
            }
        }
        if (node.parent != null) {
            if (isLeftChild(node)) node.parent.balancingFactor -=1; else node.parent.balancingFactor += 1;
            if (node.parent.balancingFactor == 0) {
                checkBalancingFactorInsert (node.parent);
            }
        }
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
            if (isLeftChild(successor)) successorParent.balancingFactor -=1; else successorParent.balancingFactor +=1;
        }
        return new Node[] {successor, successorParent};
    }
    
    public static void displayTree( Node root ) {
        if (root != null) {
            displayTree(root.left);
            System.out.print(" " + root.data + "(" + root.balancingFactor + ")");
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
