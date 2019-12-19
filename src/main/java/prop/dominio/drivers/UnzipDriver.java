package prop.dominio.drivers;

import prop.dominio.UnzipTransaction;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class UnzipDriver {

  private Scanner scanner = new Scanner(System.in);
  private Scanner scanner2 = new Scanner(System.in);

  public static void main(String[] args) {
    new UnzipDriver();
  }

  private UnzipDriver() {
    System.out.println("Welcome to the driver of the Unzip transaction");
    start();
  }

  private void start() {
    System.out.println("Path: ");
    String path = scanner.nextLine();
    try {
      new UnzipTransaction(path, path).execute();
      System.out.println("File successfully decompressed.");
      end();
    } catch (UnsupportedEncodingException uee) {
      System.out.println("Please, provide a supported extension (sippy)");
      start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void end() {
    System.out.println("\n\n");
    System.out.println("Select option:\n\t1. Start over\n\t2. Exit");
    if (scanner2.nextInt() == 1) {
      start();
    }
  }
}
