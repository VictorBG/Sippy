package algorithms.lz78;

import algorithms.base.BaseAlgorithm;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Author: Victor Blanco
 * <p>
 * Implementation of the LZ78 algorithm.
 * <p>
 * Improvements:
 * - Variable number of bits on index. We can assume that the x index wont have a
 * number greater than x-1, so we can define that the number of bits that this position can take as
 * maximum is log2(x) instead of taking 24 bits always, which also puts a theoric limit of 2^24
 * values for the index. It is also expensive for low sized files.
 * - Use a Trie instead of a HashMap. It will improve but not much.
 */
public class LZ78 implements BaseAlgorithm {


  @Override
  public byte[] encode(byte[] data) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    HashMap<String, Integer> dictionary = new HashMap<>();

    String s = "";
    Integer pos = 0;
    int index = 1;
    String text = new String(data);
    for (String b : text.split("")) {
      s += String.valueOf(b);
      if (!dictionary.containsKey(s)) {
        dictionary.put(s, index++);
        try {
          baos.write(new Pair(pos, b.charAt(0)).getBytes());
        } catch (IOException ignore) {
        }
        s = "";
        pos = 0;
      } else {
        pos = dictionary.get(s);
      }
    }

    if (pos != 0) {
      try {
        baos.write(new Pair(pos, Character.MIN_VALUE).getBytes());
      } catch (IOException ignore) {
      }
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

    return result.toString().getBytes();
  }

  private String getString(HashMap<Integer, Pair> dic, int value) {
    if (dic.get(value).getFirst() == 0) {
      return charAt(dic, value);
    } else {
      return getString(dic, dic.get(value).getFirst()) + charAt(dic, value);
    }
  }

  private String charAt(HashMap<Integer, Pair> dic, int pos) {
    Character res;
    if ((res = dic.get(pos).getSecond()) == Character.MIN_VALUE) {
      return "";
    }
    return String.valueOf(res);
  }

  private int byteArrayToInt(byte[] b) {
    return
        (b[2] & 0xFF) |
            (b[1] & 0xFF) << 8 |
            (b[0] & 0xFF) << 16;
  }

  @Override
  public byte[] readFile(File file) throws IOException {
    BufferedReader bufRdr = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
    return bufRdr.lines().map(i -> i + "\n").reduce(String::concat).get().getBytes();
  }
}
