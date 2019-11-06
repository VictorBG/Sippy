import algorithms.Algorithm;
import domain.Compress;
import java.util.Scanner;

class ConsoleApp {

  static void start() {
    new ConsoleApp();
  }

  private ConsoleApp() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("File path: ");
    String path = scanner.nextLine();
    new Compress(Algorithm.LZ78, path).execute();
  }
}
