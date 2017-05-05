/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

/**
 *
 * @author joaosouza
 */
public class AVL {
    public static void main(String arg[]){
//        AVL_tree_test();
//        AVL_tree_test3();
        AVL_tree_test4();
    }
    
    public static void AVL_tree_test() {
        AVLTree b = new AVLTree();
        b.insert(1);
        b.insert(2);
        b.insert(3);
        b.insert(4);
        b.insert(5);
        b.insert(6);
        b.insert(7);
        b.displayTree(b.root);
        b.delete(4);
        b.delete(7);
        b.delete(6);
        System.out.println("\n");
        b.displayTree(b.root);
        /*b.insert(10);
        b.insert(0);
        System.out.println("\n");
        b.displayTree(b.root);*/
    }
    
    //http://stackoverflow.com/questions/3955680/how-to-check-if-my-avl-tree-implementation-is-correct
    public static void AVL_tree_test3() {
        AVLTree b = new AVLTree();
        b.insert(20);
        b.insert(4);
        b.insert(26);
        b.insert(3);
        b.insert(9);
        b.insert(21);
        b.insert(30);
        b.insert(2);
        b.insert(7);
        b.insert(11);
        b.displayTree(b.root);
        // Case 3a: Insert 15
        b.insert(15);
        System.out.println("\n");
        b.displayTree(b.root);
        b.delete(4);
        b.displayTree(b.root);
    }

    private static void AVL_tree_test4() {
        AVLTree b = new AVLTree();
        b.insert(3);
        b.insert(4);
        b.insert(5);
        b.displayTree(b.root);
        b.delete(4);
        System.out.println("\n");
        b.displayTree(b.root);
    }
}
