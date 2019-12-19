package prop.algorithms.lz78;

/**
 * Author: Victor Blanco
 *
 * @class Pair
 * @brief Una classe Pair simple que conté un primer i un segon objecte.
 * En aquest cas, el primer és un integer i el segon un char ja que
 * s'utilitza principalment per a l'algorisme {@link LZ78} tot i que es
 * podria fer més genèric si s'escau.
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
