import domain.Transaction;
import domain.Unzip;
import domain.Zip;
import domain.algorithms.Algorithm;
import domain.model.ItemC;
import domain.model.ItemNC;
import domain.model.Statistics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Author: Victor Blanco.
 * <p>
 * Implementation of presentation layer used for internal testing.
 */
class ConsoleApp {

  static void start() {
    new ConsoleApp();
  }

  private ConsoleApp() {
    do {
      Scanner scanner = new Scanner(System.in);
      System.out.println("File path: ");
      String path = scanner.nextLine();

      System.out.println("1. Zip\n2.Unzip\nChoose:");
      int n = scanner.nextInt();

      if (n == 1) {
        Transaction<Statistics> zip = null;
        try {
          zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZ78);
          zip.execute();
          System.out.println(zip.getResult().toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else if (n == 2) {
        Transaction<Statistics> zip = new Unzip(new ItemC(new File(path)));
        try {
          zip.execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } while (true);
  }
}
