package domain.algorithms;

import domain.algorithms.base.BaseAlgorithm;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.stream.Stream;

/**
 * Author: Victor Blanco
 * <p>
 * Implementation of the LZ78 algorithm.
 * <p>
 * TODO: Make it work correctly. And decode.
 */
public class LZ78 implements BaseAlgorithm {

  @Override
  public byte[] encode(byte[] data) {
    String file = new String(data);
    String[] input = file.split("");
    ArrayList<String> dictionary = new ArrayList<>();
    HashMap<Integer, String> codeWord = new HashMap<>();

    int index = 0;
    String current = "";

    int key = 0;
    dictionary.add(null);

    while (index < input.length - 1) {
      current = input[index];

      if (dictionary.contains(current)) {
        StringBuilder previous = new StringBuilder();
        current = add(current, dictionary, input, index);

        final int[] j = {index};
        Stream.of(current).forEach((i) -> previous.append(input[j[0]++]));

        codeWord.put(key++,
            (dictionary.indexOf(previous.toString()) + ":" + current.charAt(current.length() - 1)));
        index += current.length();
      } else {
        codeWord.put(key++, "0:" + current);
        index++;
      }
      dictionary.add(current);
    }
    return codeWord.toString().getBytes();
  }

  @Override
  public byte[] decode(byte[] input) {
    return new byte[0];
  }

  private static String add(String current, ArrayList<String> dictionary, String[] in, int index) {
    for (int i = index; i < in.length; i++) {
      if (dictionary.contains(current)) {
        current = current + in[i + 1];
      }
    }
    return current;
  }
}
