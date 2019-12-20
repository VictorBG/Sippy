package prop.dominio.drivers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;
import prop.utils.FileUtils;

public class JPEGDriver {

  private BaseAlgorithm algorithm = Algorithm.JPEG.getAlgorithm();
  private String name = Algorithm.JPEG.name();

  private Scanner scanner;

  public static void main(String[] args) {
    new JPEGDriver();
  }

  private JPEGDriver() {
    scanner = new Scanner(System.in);
    start();
  }

  private void start() {
    System.out.println("Welcome to the driver of the algorithm " + name);
    try {
      encodeFile();
    } catch (IOException e) {
      System.out.println(
          "An exception has been occurred, please check that the file path provided is correct");
    }
  }

  private void encodeFile() throws IOException {
    System.out
        .println("\nProvide the file path you want to test (only ppm extension is supported):  ");
    String path = scanner.next();
    if (!"ppm".equals(FileUtils.getFileExtension(path))) {
      System.out.println("\nOther extensions that are not ppm are not supported\n");
      encodeFile();
    }
    File inputFile = new File(path);
    byte[] fileEncoded = algorithm.encode(algorithm.readFile(inputFile));
    byte[] fileDecoded = algorithm.decode(fileEncoded);

    String outputPath =inputFile.getPath().replace(".ppm",  "_decoded.ppm");
    File outputFile = new File(outputPath);

    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(outputFile));
    dataOutputStream.write(fileDecoded);
    dataOutputStream.close();

    System.out.println("\nFile successfully encoded and decoded\n");
    System.out.println("\nOpening file...\n");
    FileUtils.openFile(outputFile);

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
