import java.io.File;
import java.io.IOException;
import java.util.Optional;

import algorithms.LZ78;
import java.util.Scanner;
import utils.FileIO;

class Test {

  static void start() {
    new Test();
  }

  private Test() {
    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println("File path: ");
      String path = scanner.nextLine();

      String test = FileIO.readFileAsString(new File(path));
      Optional<String> lz78Encoded = new LZ78().encode(test);
      lz78Encoded
          .ifPresent((sippy) -> FileIO.save(FileIO.changeExtension(path, "sippy"), sippy));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
