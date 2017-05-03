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
    }
    
}
