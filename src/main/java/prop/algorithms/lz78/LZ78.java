package prop.algorithms.lz78;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

/**
 * Author: Victor Blanco
 *
 * @class LZ78
 * @brief Implementation of the LZ78 algorithm.
 *
 *     Improvements:
 *     - Variable number of bits on index. We can assume that the x index wont have a
 *     number greater than x-1, so we can define that the number of bits that this position can take
 *     as maximum is log2(x) instead of taking 24 bits always, which also puts a theoric limit of 2^24
 *     values for the index. It is also expensive (in terms of size) for low sized files.
 *     - Use a Trie instead of a HashMap for storing the dictionary.
 */
public class LZ78 implements BaseAlgorithm {

  private ByteArrayOutputStream baos;

  @Override
  public byte[] encode(byte[] data) {
    baos = new ByteArrayOutputStream();
    HashMap<String, Integer> dictionary = new HashMap<>();

    String s = "";
    Integer pos = 0;
    int index = 1;
    for (byte b : data) {
      char c = getChar(b);
      s += c;
      if (!dictionary.containsKey(s)) {
        dictionary.put(s, index++);
        write(new Pair(pos, c).getBytes());
        s = "";
        pos = 0;
      } else {
        pos = dictionary.get(s);
      }
    }

    if (pos != 0) {
      write(new Pair(pos, Character.MIN_VALUE).getBytes());
    }

    return baos.toByteArray();
  }

  @Override
  public byte[] decode(byte[] input) {
    StringBuilder result = new StringBuilder();
    HashMap<Integer, Pair> dictionary = new HashMap<>();

    int k = 1;

    for (int i = 0; i < input.length; i += 4) {
      int number = byteArrayToInt(new byte[]{input[i], input[i + 1], input[i + 2]});
      int value = input[i + 3];
      if (value < 0) {
        value += 256;
      }
      char data = (char) value;
      dictionary.put(k, new Pair(number, data));
      result.append(getString(dictionary, k++));
    }

    return result.toString().getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Writes a chunk of data to the {@link #baos} output stream.
   *
   * @param data Data to write
   */
  private void write(byte[] data) {
    try {
      baos.write(data);
    } catch (IOException ignore) {}
  }

  // TODO: These 2 methods can be implemented better

  /**
   * Returns a String containing the recovered word from the dictionary.
   *
   * It reads the value element and retrieves the {@link Pair} object associated
   * with it, it then returns the char that the pair contains concated with the
   * string returned by this method for the value contained by the pair.
   *
   * If the value contained by the pair is 0, it means it reached the final of the string
   * recovery, and will not iterate more.
   *
   * Note: The char is always concated at the end, as it is the last char, and the rest of the char
   * are stored as a list of values that points to another position of the map.
   *
   * @param dic   Dictionary to read from
   * @param value Value to read from the dictionary
   *
   * @return The recovered string of the map starting with the value object
   */
  private String getString(HashMap<Integer, Pair> dic, int value) {
    if (dic.get(value).getFirst() == 0) {
      return charAt(dic, value);
    } else {
      return getString(dic, dic.get(value).getFirst()) + charAt(dic, value);
    }
  }

  /**
   * Returns the char contained in the position (or value) of the dictionary. If the char
   * is a {@link Character#MIN_VALUE} it returns an empty string, if not it returns
   * the char of the position.
   *
   * @param dic Dictionary to read from
   * @param pos Position (value) to read from the dictionary
   *
   * @return the char of the position pos of the dictionary
   */
  private String charAt(HashMap<Integer, Pair> dic, int pos) {
    char res;
    if ((res = dic.get(pos).getSecond()) == Character.MIN_VALUE) {
      return "";
    }
    return String.valueOf(res);
  }

  /**
   * Returns a char based on the byte value.
   *
   * If the value is negative (ca2), it is converted into a positive value, to match the ASCII
   * table, if not, it is return as it is
   *
   * @param b Byte to convert
   *
   * @return The byte converted into an ASCII table char
   */
  private char getChar(byte b) {
    int i = new Byte(b).intValue();
    if (i < 0) {
      i += 256;
    }
    return (char) i;
  }

  /**
   * Converts the given byte array of length 3 into an int
   *
   * @param b Byte array to convert
   *
   * @return Integer contained in the byte array
   */
  private int byteArrayToInt(byte[] b) {
    return
        (b[2] & 0xFF) |
            (b[1] & 0xFF) << 8 |
            (b[0] & 0xFF) << 16;
  }

  @Override
  public Algorithm getAlgorithmUsed() {
    return Algorithm.LZ78;
  }
}
