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
class Node{
    public int data;
    public Node left = null;
    public Node right = null;
    public Node parent = null;
    public int balancingFactor = 0;
    public Node(int d){
        this.data = d;
    }
    public Node(int d, Node l) {
        this.data = d;this.left = l;
    }
    public Node(int d, Node l, Node r) {
        this.data = d;this.left = l;this.right = r;
    }
    public Node (int d, Node l, Node r, Node p) {
        this.data = d;this.left = l;this.right = r;this.parent = p;
    }
    public Node(int d, Node l, Node r, Node p, int b) {
        this.data = d;this.left = l;this.right = r;this.parent = p;this.balancingFactor = b;
    }
}
