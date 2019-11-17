package dominio.drivers;

import algorithms.Algorithm;
import dominio.Zip;
import dominio.model.ItemNC;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ZipDriver {

  private Scanner scanner = new Scanner(System.in);
  private Scanner scanner2 = new Scanner(System.in);

  public static void main(String[] args) {
    new ZipDriver();
  }

  private ZipDriver() {
    System.out.println("Welcome to the driver of the Zip transaction");
    start();
  }

  private void start() {
    System.out.println("Path: ");
    String path = scanner.nextLine();
    try {
      Algorithm algorithm = selectAlgorithm();
      System.out.println("Compressing file using: " + algorithm.name());
      new Zip(ItemNC.create(new File(path)), algorithm).execute();
      System.out.println("File successfully compressed.");
      end();
    } catch (UnsupportedEncodingException uee) {
      System.out.println("Please, provide a supported extension (txt or ppm)");
      start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Algorithm selectAlgorithm() {
    System.out.println("Select algorithm: \n\t1. LZ78\n\t2. LZW\n\t3. JPEG\n\t4. LZSS");
    int algorithmSelected = scanner2.nextInt();
    if (algorithmSelected < 1 || algorithmSelected > 4) {
      return selectAlgorithm();
    } else {
      return Algorithm.valueOf((byte) (algorithmSelected - 1));
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
