package algorithms.lz78;

import algorithms.base.BaseAlgorithm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    int j=0;
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
    for (i = 0; i < input.length; i += 3) {
      int number = byteArrayToInt(new byte[]{input[i], input[i + 1]});
      int value = input[i + 2];
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
      return String.valueOf(dictionary.get(start).getSecond());
    }

    return getDicString(dictionary, dictionary.get(start).getFirst()) + dictionary
        .get(start).getSecond();
  }

  private int byteArrayToInt(byte[] b) {
    return
        (b[1] & 0xFF) |
            (b[0] & 0xFF) << 8;
  }

  @Override
  public byte[] readFile(File file) throws IOException {
    BufferedReader bufRdr = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
    return bufRdr.lines().map(i -> i + "\n").reduce(String::concat).get().getBytes();
  }
}
