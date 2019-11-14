import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import domain.Transaction;
import domain.Unzip;
import domain.Zip;
import domain.algorithms.Algorithm;
import domain.model.ItemC;
import domain.model.ItemNC;
import domain.model.Statistics;

/**
 * Author: Sergio Vazquez
 * <p>
 * Presentation layer for first deliverable
 */


public class OnApplicationStart {
  Transaction<Statistics> zip;

  static void start() {
    new OnApplicationStart();
  }

  private OnApplicationStart() {
    Scanner keyBoard = new Scanner(System.in);
    int compressDecscompress = -1;
    int automaticManual = -1;
    int algorithm = -1;
    String path = "";
    boolean canContinue;

    while (true) {
      canContinue = false;
      System.out.println(
          "Welcome to Sippy, your favorite compressor \n What do you wish? \n 1. Compress 2. Descompress \n");
      do {
        try {
          System.out.print("Enter 1 or 2 : ");
          compressDecscompress = keyBoard.nextInt();
          if (compressDecscompress == 2 || compressDecscompress == 1) {
            canContinue = true;
          }
        } catch (InputMismatchException ex) {
          keyBoard.next();
        }
      }
      while (!canContinue);

      canContinue = false;
      // We are going to compress
      if (compressDecscompress == 1) {
        zip = null;
        System.out.print("You've selected compress, now we need your file path: ");
        path = keyBoard.next();
        System.out.print(
            "\nThanks, as you can see, this is a very complete program \n so please indicate if you want automatic or manual compression: \n \n 1. Automatic  2. Manual\n ");
        do {
          try {
            System.out.print("Enter 1 or 2 : ");
            automaticManual = keyBoard.nextInt();
            if (automaticManual == 2 || automaticManual == 1) {
              canContinue = true;
            }
          } catch (InputMismatchException ex) {
            keyBoard.next();
          }
        }
        while (!canContinue);

        if (automaticManual == 1) {
          try {
            zip = new Zip(ItemNC.create(new File(path)), Algorithm.AUTOMATIC);
            zip.execute();
          } catch (IOException e) {
            e.printStackTrace();
          }
          canContinue = true;
        } else {
          System.out
              .print("\nAl right, choose now the algorithm:\n 1. LZ78 2. LZSS 3. LZW 4. JPEG \n");
          do {
            System.out.print("Enter 1, 2, 3 or 4 : ");
            algorithm = keyBoard.nextInt();
            if (algorithm == 1 || algorithm == 2 || algorithm == 3 || algorithm == 4) {
              canContinue = true;
            }

          }
          while (!canContinue);
          try {
            switch (algorithm) {
              case 1:
                System.out.print("\nCompressing using LZ78 \n");
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZ78);
                zip.execute();

                break;
              case 2:
                System.out.print("\nCompressing using LZSS \n");
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZSS);
                zip.execute();
                break;
              case 3:
                System.out.print("\nCompressing using LZW \n \n");
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZW);
                zip.execute();

                break;
              case 4:
                System.out.print("\nCompressing using JPEG \n");
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.JPEG);
                zip.execute();

                break;

              default:
                break;

            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          showStatistics();
        }

      } else if (compressDecscompress == 2) {
        System.out.print("You've selected Decompress, now we need your file path: ");
        path = keyBoard.next();
        Transaction<Void> zip = new Unzip(new ItemC(new File(path)));
        try {
          zip.execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  private void showStatistics(){
    NumberFormat formatter = new DecimalFormat("#0.00000");
    double time = zip.getResult().getElapsedTime();
    time = time/1000;
    double initialSize = zip.getResult().getInitialSize();
    double finalSize = zip.getResult().getFinalSize();
    double compression = (finalSize/initialSize) * 100.0;

    System.out.print("\n Your compression has finished.\n ");
    System.out.print("\n Statistics: \n " + "Elapsed time: " + formatter.format(time) +" seconds"+ "\n The initial size was: "
        + initialSize +  " Bytes"+ "\n And the final is: " + finalSize + " Bytes"+ "\n Compression: " + formatter.format(compression) +" %"+"\n\n");

  }
}


