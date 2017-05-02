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

// vermelha = 1
// preta = 0

class Node{
    public int data;
    public Node left = null;
    public Node right = null;
    public Node parent = null;
    //public int balancingFactor = 0;
    public int cor;
    public Node(int d){
        this.data = d;
    }
    public Node (int d, int cor) {
        this.data = d;
        this.cor = cor;
    }
    public Node (int d, int cor, Node parent) {
        this.data = d;
        this.parent = parent;
        this.cor = cor;
    }
}
