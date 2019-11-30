package prop.algorithms.lz78;

/**
 * @class Pair
 * Author: Victor Blanco
 */
public class Pair {

  private int first;
  private char second;

  Pair(int first, char second) {
    this.first = first;
    this.second = second;
  }

  int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  char getSecond() {
    return second;
  }

  public void setSecond(char second) {
    this.second = second;
  }

  byte[] getBytes() {
    return new byte[]{
        (byte) ((first >> 16) & 0xFF),
        (byte) ((first >> 8) & 0xFF),
        (byte) (first & 0xFF),
        (byte) second
    };
  }
}
