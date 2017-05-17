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
//        test1();
test2();
    }
    
    public static void test1() {
        BlackRedTree b = new BlackRedTree();
        b.insert(3);
        b.insert(2);
        b.insert(4);
        b.insert(5);
        b.insert(6);
        b.displayTree(b.root);
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
        b.generateTreeView(b.root, "avlTree1.html");
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
        b.generateTreeView(b.root, "avlTree1.html");
        System.out.println("\n");
        b.delete(10);
        b.generateTreeView(b.root, "avlTree2.html");
        b.delete(8);
        b.displayTree(b.root);
        b.generateTreeView(b.root, "avlTree3.html");
    }
    
}
