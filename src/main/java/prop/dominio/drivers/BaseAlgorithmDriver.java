package prop.dominio.drivers;

import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import prop.utils.FileUtils;

/**
 * Author: Victor Blanco
 *
 * Driver de la classe BaseAlgorithm. Gestiona totes les operacions
 * del driver que corresponen a un determinat algorisme.
 */
public abstract class BaseAlgorithmDriver {

  protected abstract Algorithm getAlgorithm();

  private BaseAlgorithm algorithm = getAlgorithm().getAlgorithm();
  private String name = getAlgorithm().name();

  private Scanner scanner;

  /**
   * Escàner que només s’utilitza per obtenir l’entrada en cas que l’usuari seleccioni l’opció
   * d'String.
   * Si l'utilitzat és {@link #scanner}, no es llegirà tot correctament degut a que s'ha escrit
   * anteriorment (per seleccionar l'opció).
   */
  private Scanner scannerString;

  public BaseAlgorithmDriver() {
    scanner = new Scanner(System.in);
    scannerString = new Scanner(System.in);
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
    String test = scannerString.nextLine();
    byte[] output = algorithm.encode(test.getBytes());
    System.out.println("Encoded: " + new String(output));
    byte[] a = algorithm.decode(output);
    System.out.println("Decoded: " + new String(a));
    end();
  }

  private void encodeFile() throws IOException {
    System.out
        .println("\nProvide the file path you want to test (only txt extension is supported):  ");
    String path = scanner.next();
    if (!"txt".equals(FileUtils.getFileExtension(path))) {
      System.out.println("\nOther extensions that are not txt are not supported\n");
      encodeFile();
    }
    byte[] output = algorithm.encode(algorithm.readFile(new File(path)));
    System.out.println("Encoded: " + new String(output));
    System.out.println("Decoded: " + new String(algorithm.decode(output)));
    end();
  }

  private void end() {
    System.out.println("\n\n");
    System.out.println("Select option:\n\t1. Start over\n\t2. Exit");
    if (scanner.nextInt() == 1) {
      start();
    }
  }

}
