import java.io.File;
import java.io.IOException;
import java.util.Optional;

import algorithms.LZ78;
import utils.FileIO;

class Test {

  static void start() {
    new Test();
  }

  private Test() {
    try {
      String test = FileIO.readFileAsString(new File("C:\\Users\\Victor\\Desktop\\test.txt"));
      Optional<String> estoyMamadisimo = new LZ78().encode(test);
      estoyMamadisimo
          .ifPresent((sippy) -> FileIO.save("C:\\Users\\Victor\\Desktop\\test.sippy", sippy));

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
