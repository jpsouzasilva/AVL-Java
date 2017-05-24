/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorerubronegra;

/**
 *
 * @author joaosouza
 */
public class ArvoreRubroNegra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//       test1();
//       test2();
//       test3();
       test4();
//       test5();
    }
    
    public static void test1() {
        BlackRedTree b = new BlackRedTree();
        b.insert(3);
        b.insert(2);
        b.insert(4);
        b.insert(5);
        b.insert(6);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT1-1.html");
        System.out.println("\n");
        b = new BlackRedTree();
        b.insert(10);
        b.insert(22);
        b.insert(6);
        b.insert(3);
        b.insert(8);
        b.insert(7);
        b.insert(9);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT1-2.html");
    }
    
    public static void test2() {
        BlackRedTree b = new BlackRedTree();
        b.insert(6);
        b.insert(4);
        b.insert(10);
        b.insert(8);
        b.insert(14);
        b.insert(7);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT2-1.html");
        System.out.println("\n");
        b.delete(10);
        b.generateTreeView(b.root, "avlTreeT2-2.html");
        b.delete(8);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT2-3.html");
    }
    
    public static void test3() {
        BlackRedTree b = new BlackRedTree();
        b.insert(7);
        b.insert(2);
        b.insert(10);
        b.insert(1);
        b.insert(11);
        b.insert(5);
        b.displayTree(b.root);
        System.out.println("\n");
        b.delete(2);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT3-1.html");
    }
    
    public static void test4() {
        BlackRedTree b = new BlackRedTree();
        b.insert(5);
        b.insert(3);
        b.insert(8);
        b.insert(2);
        b.insert(7);
        b.insert(10);
        b.insert(4);
        b.insert(11);
        b.insert(9);
        b.insert(6);
        b.insert(20);
        b.insert(21);
        b.insert(24);
        b.insert(1);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTreeT4-1.html");
        b.delete(8);
        b.generateTreeView(b.root, "avlTreeT4-2.html");
        b.delete(9);
        b.generateTreeView(b.root, "avlTreeT4-3.html");
        b.delete(20);
        b.generateTreeView(b.root, "avlTreeT4-4.html");
    }
    
    public static void test5() {
        BlackRedTree b = new BlackRedTree();
        b.insert(1);
        b.insert(2);
        b.insert(3);
        b.displayTree(b.root);
        System.out.println("\n");
        b = new BlackRedTree();
        b.insert(1);
        b.insert(3);
        b.insert(2);
        b.displayTree(b.root);
        System.out.println("\n");
        b = new BlackRedTree();
        b.insert(3);
        b.insert(2);
        b.insert(1);
        b.displayTree(b.root);
        System.out.println("\n");
        b = new BlackRedTree();
        b.insert(3);
        b.insert(1);
        b.insert(2);
        b.displayTree(b.root);
        System.out.println("\n");
    }
    
}
