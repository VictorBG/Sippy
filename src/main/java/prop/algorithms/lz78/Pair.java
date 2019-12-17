package prop.algorithms.lz78;

/**
 * Author: Victor Blanco
 *
 * @class Pair
 *
 *     A simple Pair class that holds a first and a second object, in that case, first
 *     is an integer and second is a char, as it is used mainly for the {@link LZ78} algorithm
 *     but it could be more generic if neccessary.
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

  char getSecond() {
    return second;
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
