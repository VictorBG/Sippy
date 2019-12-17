package prop.presentacion;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import prop.algorithms.Algorithm;
import prop.dominio.Zip;
import prop.dominio.Transaction;
import prop.dominio.Unzip;
import prop.dominio.model.Statistics;

/**
 * @class ConsoleApp
 * @brief: Presentation layer for first deliverable
 *     Author: Sergio Vazquez
 */


public class ConsoleApp {

  private Transaction<Statistics> zip;

  public static void start() {
    new ConsoleApp();
  }

  /**
   * @brief Constructora
   *     \pre cert
   *     \post S'executa la part visual del programa.
   */
  private ConsoleApp() {

    Scanner keyBoard = new Scanner(System.in);
    int compressDecscompress = -1;
    int automaticManual = -1;
    int algorithm = -1;
    String path = "";
    boolean canContinue;

    while (true) {
      canContinue = false;
      System.out.println(
          "\n\nWelcome to Sippy, your favorite compressor \n What do you wish? \n 1.Compress    2.Decompress   3.Exit\n");
      do {
        try {
          System.out.print("Enter 1 or 2 : ");
          compressDecscompress = keyBoard.nextInt();
          if (compressDecscompress == 2 || compressDecscompress == 1 || compressDecscompress == 3) {
            canContinue = true;
          }
        } catch (InputMismatchException ex) {
          keyBoard.next();
        }

      }
      while (!canContinue);
      if (compressDecscompress == 3) { break; }
      canContinue = false;
      // We are going to compress
      if (compressDecscompress == 1) {
        zip = null;
        System.out.print("You've selected compress, now we need your file path: ");
        path = keyBoard.next();
        System.out.print(
            "\nThanks, as you can see, this is a very complete program \n so please indicate if you want automatic or manual compression: \n \n 1.Automatic    2.Manual   3.Insert Path again   4.Exit\n ");
        do {
          try {
            System.out.print("Enter 1, 2 or 3 for reentering the path : ");
            automaticManual = keyBoard.nextInt();
            if (automaticManual == 2 || automaticManual == 1 || automaticManual == 4) {
              canContinue = true;
            } else if (automaticManual == 3) {
              path = keyBoard.next();
            }
          } catch (InputMismatchException ex) {
            keyBoard.next();
          }
        }
        while (!canContinue);

        if (automaticManual == 4) { break; }

        if (automaticManual == 1) {
          try {
            zip = new Zip(path, path, Algorithm.AUTOMATIC);
            zip.execute();
          } catch (IOException e) {
            e.printStackTrace();
          }
          canContinue = true;
        } else {
          System.out.print(
              "\nAl right, choose now the algorithm:\n 1.LZ78     2.LZW    3.JPEG    4.LZSS    5.Exit\n");
          do {
            System.out.print("Enter 1, 2, 3 or 4 : ");
            algorithm = keyBoard.nextInt();
            if (algorithm == 1 || algorithm == 2 || algorithm == 3 || algorithm == 4
                || algorithm == 5) {
              canContinue = true;
            }

          }
          while (!canContinue);
          if (algorithm == 5) { break; }
          try {
            Algorithm a = Algorithm.valueOf((byte) (algorithm - 1));
            System.out.print("\nCompressing using " + Objects.requireNonNull(a).name());
            zip = new Zip(path, path, a);
            zip.execute();

          } catch (IOException e) {
            e.printStackTrace();
          }
          showStatistics();
        }

      } else if (compressDecscompress == 2) {
        System.out.print("You've selected Decompress, now we need your file path: ");
        path = keyBoard.next();
        Transaction<Statistics> zip = new Unzip(path, Paths.get(path).getRoot().toString());
        try {
          zip.execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
        NumberFormat formatter = new DecimalFormat("#0.00000");
        double time = zip.getResult().getElapsedTime();
        time = time / 1000;
        System.out.print("\n Your decompression has finished.\n ");
        System.out.print(
            "\n Statistics: \n " + "Elapsed time: " + formatter.format(time) + " seconds\n\n");
      }
    }
  }

  private void showStatistics() {
    NumberFormat formatter = new DecimalFormat("#0.00000");
    double time = zip.getResult().getElapsedTime();
    time = time / 1000;
    double initialSize = zip.getResult().getInitialSize();
    double finalSize = zip.getResult().getFinalSize();
    double compression = ((initialSize - finalSize) / initialSize) * 100.0;

    System.out.print("\n Your compression has finished.\n ");
    System.out.print("\n Statistics: \n " + "Elapsed time: " + formatter.format(time) + " seconds"
        + "\n The initial size was: "
        + initialSize + " Bytes" + "\n And the final is: " + finalSize + " Bytes"
        + "\n Compression: " + formatter.format(compression) + " %" + "\n\n");

  }
}


