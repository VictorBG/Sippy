package algorithms;

import algorithms.base.BaseAlgorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import java.util.stream.Stream;

/*
Just a test implementation, I'm sure it does not work well
 */
public class LZ78 implements BaseAlgorithm<byte[], byte[]> {

  @Override
  public Optional<byte[]> encode(byte[] bytes) {
    String file = new String(bytes);
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
    return Optional.of(codeWord.toString().getBytes());
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
