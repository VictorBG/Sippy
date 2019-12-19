package prop.presentacion;

import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import prop.algorithms.Algorithm;

/**
 * Author: Sergio Vazquez
 *
 * @class ConsoleApp
 * @brief: Presentation layer for first deliverable
 */


public class ConsoleApp implements InterfacePanelContract {


  private InterfaceController interfaceController;

  public static void start() {
    new ConsoleApp();
  }

  /**
   * @brief Constructora
   *     \pre cert
   *     \post S'executa la part visual del programa.
   */
  private ConsoleApp() {

    interfaceController = new InterfaceController(this);

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
          interfaceController.onCompressClick(path, path, 1);
        } else {
          System.out.print(
              "\nAl right, choose now the algorithm:\n 1.LZ78    2.LZW    3.JPEG    4.LZSS    5.Exit\n");
          do {
            System.out.print("Enter the number : ");
            algorithm = keyBoard.nextInt();
          }
          while (algorithm < 1 || algorithm > 5);

          if (algorithm == 5) { break; }

          Algorithm a = Algorithm.valueOf((byte) (algorithm - 1));
          System.out.println("\nCompressing using " + Objects.requireNonNull(a).name());
          interfaceController.onCompressClick(path, path, algorithm - 1);
        }

      } else {
        System.out.print("You've selected Decompress, now we need your file path: ");
        path = keyBoard.next();

        interfaceController.onDecompressClick(path, Paths.get(path).getParent().toString());
      }
    }
  }


  @Override
  public void showStatistics(String statistics) {
    System.out.println(statistics);
  }

  @Override
  public void showAlert(String message, String title) {
    System.out.println("\033[0;31m" + title + "\033[0;37m : " + message);
  }
}


