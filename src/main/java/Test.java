
import java.util.Scanner;

class Test {

  static void start() {
    new Test();
  }

  private Test() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("File path: ");
    String path = scanner.nextLine();
    new Compress(new Params(path, Algorithms.LZ78)).execute();
  }
}
