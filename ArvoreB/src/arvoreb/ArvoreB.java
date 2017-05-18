/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreb;

/**
 *
 * @author joaopaulo
 */
public class ArvoreB {

    public static void main(String[] args) throws Exception {
        test1(new BTree(2));
    }
    
    public static void test1(BTree btree) throws Exception {
        btree.insert('a');
        btree.insert('b');
        btree.insert('c');
        btree.insert('d');
        btree.insert('e');
        btree.find('e');
    }
    
}
