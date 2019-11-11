package domain.algorithms;

import domain.algorithms.base.BaseAlgorithm;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.stream.Stream;
import utils.Bytes;

/**
 * Author: Victor Blanco
 * <p>
 * Implementation of the LZ78 algorithm.
 * <p>
 */
public class LZ78 implements BaseAlgorithm {


  @Override
  public byte[] encode(byte[] data) {
    List<String> dictionary = new ArrayList<>();
    byte[] result = new byte[0];

    String s = "";
    int pos = 0;
    for (String b : new String(data).split("")) {
      s += String.valueOf(b);
      if (!dictionary.contains(s)) {
        dictionary.add(s);
        result = Bytes.concat(result, new Pair(pos, s.charAt(s.length() - 1)).getBytes());
        pos = 0;
        s = "";
      } else {
        pos = dictionary.indexOf(s);
      }
    }
    return result;
  }

  @Override
  public byte[] decode(byte[] input) {
    StringBuilder result = new StringBuilder();
    HashMap<Integer, Pair> dictionary = new HashMap<>();

    int k = 0, j = 0;

    int i;
    for (i = 0; i < input.length; i += 4) {
      int number = byteArrayToInt(new byte[]{input[i], input[i + 1], input[i + 2]});
      int value = input[i + 3];
      if (value < 0) {
        value += 256;
      }
      char data = (char) value;
      dictionary.put(k++, new Pair(number, data));
      result.append(getDicString(dictionary, j++));
    }

    return result.toString().getBytes();
  }

  private String getDicString(HashMap<Integer, Pair> dictionary, int start) {
    if (dictionary.get(start) == null) {
      return "";
    }
    if (dictionary.get(start).getFirst() == 0) {
      return String.valueOf(dictionary.get(start).second);
    }

    return getDicString(dictionary, dictionary.get(start).getFirst()) + dictionary
        .get(start).second;
  }

  private int byteArrayToInt(byte[] b) {
    return
        (b[2] & 0xFF) |
            (b[1] & 0xFF) << 8 |
            (b[0] & 0xFF) << 16;
  }

  private byte[] intToByteArray(int a) {
    return new byte[]{
        (byte) ((a >> 24) & 0xFF),
        (byte) ((a >> 16) & 0xFF),
        (byte) ((a >> 8) & 0xFF),
        (byte) (a & 0xFF)
    };
  }

  private static final class Pair {

    private int first;
    private char second;

    public Pair(int first, char second) {
      this.first = first;
      this.second = second;
    }

    public int getFirst() {
      return first;
    }

    public void setFirst(int first) {
      this.first = first;
    }

    public char getSecond() {
      return second;
    }

    public void setSecond(char second) {
      this.second = second;
    }

    public byte[] getBytes() {
      return new byte[]{
          (byte) ((first >> 16) & 0xFF),
          (byte) ((first >> 8) & 0xFF),
          (byte) (first & 0xFF),
          (byte) second
      };
    }
  }
}
