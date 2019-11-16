package dominio.drivers;

import algorithms.Algorithm;
import algorithms.base.BaseAlgorithm;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import utils.FileUtils;

/**
 * Author: Victor Blanco
 * <p>
 * Base algorithm driver. It manages all the operations of the driver that corresponds to a specific
 * algorithm.
 */
public abstract class BaseAlgorithmDriver {

  /**
   * Must be implemented by the childrens
   */
  protected abstract Algorithm getAlgorithm();

  private BaseAlgorithm algorithm = getAlgorithm().getAlgorithm();
  private String name = getAlgorithm().name();

  private Scanner scanner;

  BaseAlgorithmDriver() {
    scanner = new Scanner(System.in);
    start();
  }

  private void start() {
    System.out.println("Welcome to the driver of the algorithm " + name);
    System.out.println("\nWhat do you want to encode: \n\t1. String\n\t2. File\n");
    if (scanner.nextInt() == 1) {
      encodeString();
    } else {
      try {
        encodeFile();
      } catch (IOException e) {
        System.out.println(
            "An exception has been occurred, please check that the file path provided is correct");
      }
    }
  }

  private void encodeString() {
    System.out.println("\nProvide the text you want to test:  ");
    String test = readUntilEnter();
    byte[] output = algorithm.encode(test.getBytes());
    System.out.println("Encoded: " + new String(output));
    System.out.println("Decoded: " + new String(algorithm.decode(output)));
    start();
  }

  private void encodeFile() throws IOException {
    System.out
        .println("\nProvide the file path you want to test (only txt extension is supported):  ");
    String path = readUntilEnter();
    if (!"txt".equals(FileUtils.getFileExtension(path))) {
      System.out.println("\nOther extensions that are not txt is not supported\n");
      encodeFile();
    }
    byte[] output = algorithm.encode(algorithm.readFile(new File(path)));
    System.out.println("Encoded: " + new String(output));
    System.out.println("Decoded: " + new String(algorithm.decode(output)));
    start();
  }

  private String readUntilEnter() {
    StringBuilder result = new StringBuilder();
    String readString = scanner.nextLine();
    while (readString != null) {
      result.append(readString);
      if (readString.isEmpty()) {
        break;
      }

      if (scanner.hasNextLine()) {
        readString = scanner.nextLine();
      } else {
        readString = null;
      }
    }
    return result.toString();
  }

}
