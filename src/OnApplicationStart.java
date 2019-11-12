import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Scanner;

import domain.Transaction;
import domain.Unzip;
import domain.Zip;
import domain.algorithms.Algorithm;
import domain.model.ItemC;
import domain.model.ItemNC;
import domain.model.Statistics;


public class OnApplicationStart {

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
        Transaction<Statistics> zip = null;
        System.out.print("You've selected compress, now we need your file path: ");
        path = keyBoard.next();
        System.out.print(
            "\nThanks, as you can see, this is a very complete program \n so please indicate if you want automatic or manual compression: \n 1. Automatic  2. Manual\n ");
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

          switch (algorithm) {
            case 1:
              System.out.print("\nCompressing using LZ78 \n");
              try {
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZ78);
                zip.execute();
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            case 2:
              System.out.print("\nCompressing using LZSS \n");
              try {
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZSS);
                zip.execute();
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            case 3:
              System.out.print("\nCompressing using LZW \n \n");
              try {
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.LZW);
                zip.execute();
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            case 4:
              System.out.print("\nCompressing using JPEG \n");
              try {
                zip = new Zip(ItemNC.create(new File(path)), Algorithm.JPEG);
                zip.execute();
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            default:
              break;

          }
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
}


