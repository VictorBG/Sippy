package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import algorithms.base.BaseAlgorithm;


public class LZ78 implements BaseAlgorithm<String, String> {

  @Override
  public Optional<String> encode(String file) {
    String[] input = file.split("");
    ArrayList<String> dictionary = new ArrayList<>();
    HashMap<Integer, String> codeWord = new HashMap<>();

    int index = 0;
    String current = "";

    int key = 0;

    while (index < input.length - 1) {
      current = input[index];

      if (dictionary.contains(current)) {
        StringBuilder previous = new StringBuilder();

        current = add(current, dictionary, input, index);

        int j = index;
        for (int i = 0; i < current.length() - 1; i++) {
          previous.append(input[j]);
          j += 1;
        }
        codeWord.put(key++, (dictionary.indexOf(previous.toString()) + ":" + append(current)));
        index = index + current.length();
        dictionary.add(current);
      } else {
        codeWord.put(key++, "0:" + current);
        dictionary.add(current);
        index++;
      }
    }
    return Optional.of(codeWord.toString());
  }

  private static String add(String current, ArrayList<String> dictionary, String[] in, int index) {
    for (int i = index; i < in.length; i++) {
      if (dictionary.contains(current)) {
        current = current + in[i + 1];
      }
    }
    return current;
  }

  private static String append(String current) {
    String[] name = current.split("");
    return name[name.length - 1];
  }
}
