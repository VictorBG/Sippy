package prop.algorithms.jpeg;

/**
 * Author: Yaiza Cano
 * @class Node
 * @brief Nodes que contenen els arbres que es poden crear amb la classe BinaryTree.
 */

public class Node {

  private String value;
  private Node left;
  private Node right;

  public Node(String value) {
    setValue(value);
    right = null;
    left = null;
  }

  public Node() {
    value = null;
    right = null;
    left = null;
  }

  public String getValue() {
    return value;
  }

  public Node getLeft() {
    return left;
  }

  public Node getRight() {
    return right;
  }

  public void setValue(String st) {
    value = st;
  }

  public void setLeft(Node l) {
    left = l;
  }

  public void setRight(Node r) {
    right = r;
  }
}
