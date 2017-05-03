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
        //BinarySearchTree b = new BinarySearchTree();
        //B_tree_test(b);
        AVLTree avl = new AVLTree();
        AVL_tree_test(avl);
    }
    
    public static void AVL_tree_test(AVLTree b) {
        b.insert(3);
        b.insert(8);
        b.insert(1);
        b.insert(4);
        b.insert(6);
        b.insert(2);
        b.insert(10);
        b.insert(9);
        b.displayTree(b.root);
        b.delete(10);
        System.out.println("\n");
        b.displayTree(b.root);
        b.insert(10);
        b.insert(0);
        System.out.println("\n");
        b.displayTree(b.root);
    }
}
