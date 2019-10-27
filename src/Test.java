import algorithms.Algorithm;
import domain.Compress;
import java.util.Scanner;

class Test {

  static void start() {
    new Test();
  }

  private Test() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("File path: ");
    String path = scanner.nextLine();
    new Compress(Algorithm.LZ78, path).execute();
  }
}
