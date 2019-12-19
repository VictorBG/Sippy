package prop.algorithms.jpeg;

/**
 * @class BinaryTree
 *     Author: Yaiza Cano
 */

import java.util.ArrayList;

public class BinaryTree {

  private Node root;
  private ArrayList<Node> branques = new ArrayList<>();

  public BinaryTree() {
    root = new Node();
  }

  public Node getRoot() {
    return root;
  }

  public void createNodeLeft(Node current, String value) {
    Node n = new Node(value);
    current.setLeft(n);
  }

  public void createNodeLeft(Node current) {
    Node n = new Node();
    current.setLeft(n);
  }

  public Node getNodeLeft(Node node) {
    return node.getLeft();
  }

  public void createNodeRight(Node current, String value) {
    Node n = new Node(value);
    current.setRight(n);
  }

  public void createNodeRight(Node current) {
    Node n = new Node();
    current.setRight(n);
  }

  public Node getNodeRight(Node node) {
    return node.getRight();
  }

  public boolean isLeaf(Node n) {
    if (n.getValue() == null) {
      return false;
    }
    return true;
  }

  public void write(Node current) {
    System.out.print(current.getValue() + " ");
    if (current.getLeft() != null) {
      System.out.print(" Fill ESquerre ");
      write(current.getLeft());
    }
    if (current.getRight() != null) {
      System.out.print(" Fill dret ");
      write(current.getRight());
    }
  }
}
