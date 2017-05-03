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
        b.delete(9);
        System.out.println("\n");
        b.displayTree(b.root);
    }
    
    public static void B_tree_test(BinarySearchTree b) {
        b.insert(3);
        b.insert(8);
        b.insert(1);
        b.insert(4);
        b.insert(6);
        b.insert(2);
        b.insert(10);
        b.insert(9);
        b.insert(20);
        b.insert(25);
        b.insert(15);
        b.insert(16);
        System.out.println("Original Tree : ");
        b.display(b.root);		
        System.out.println("");
        System.out.println("Check whether Node with value 4 exists : " + b.find(4));
        System.out.println("Delete Node with no children (2) : ");
        b.delete(2);
        b.display(b.root);
        System.out.println("\nDelete Node with one child (4) : ");
        b.delete(4);
        b.display(b.root);
        System.out.println("\nDelete Node with Two children (10) : ");
        b.delete(10);
        b.display(b.root);
    }
}
