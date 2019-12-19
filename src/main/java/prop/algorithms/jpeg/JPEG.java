package prop.algorithms.jpeg;
/**
 * @class JPEG
 *     Author: Yaiza Cano
 */

import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.abs;
import static prop.algorithms.jpeg.Huffman.*;

public class JPEG implements BaseAlgorithm {

  // region Global variables
  private BinaryTree dc0Tree = new BinaryTree();
  private BinaryTree ac0Tree = new BinaryTree();
  private BinaryTree dc1Tree = new BinaryTree();
  private BinaryTree ac1Tree = new BinaryTree();
  private String output = "";
  private ByteArrayOutputStream out;
  private String version;
  private int w;
  private int h;
  private int[][] qtY = new int[][]{{16, 11, 10, 16, 24, 40, 51, 61},
      {12, 12, 14, 19, 26, 58, 60, 55},
      {14, 13, 16, 24, 40, 57, 69, 56}, {14, 17, 22, 29, 51, 87, 80, 62},
      {18, 22, 37, 56, 68, 109, 103, 77},
      {24, 35, 55, 64, 81, 104, 113, 92}, {49, 64, 78, 87, 103, 121, 120, 101},
      {72, 92, 95, 98, 112, 110, 103, 99}};
  private int[][] qtC = new int[][]{{17, 18, 24, 47, 99, 99, 99, 99},
      {18, 21, 26, 66, 99, 99, 99, 99},
      {24, 26, 56, 99, 99, 99, 99, 99}, {47, 66, 99, 99, 99, 99, 99, 99},
      {99, 99, 99, 99, 99, 99, 99, 99},
      {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99},
      {99, 99, 99, 99, 99, 99, 99, 99}};
  private float[][] dct = new float[][]{
      {0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f},
      {0.4904f, 0.4157f, 0.2778f, 0.0975f, -0.0975f, -0.2778f, -0.4157f, -0.4904f},
      {0.4619f, 0.1913f, -0.1913f, -0.4619f, -0.4619f, -0.1913f, 0.1913f, 0.4619f},
      {0.4157f, -0.0975f, -0.4904f, -0.2778f, 0.2778f, 0.4904f, 0.0975f, -0.4157f},
      {0.3536f, -0.3536f, -0.3536f, 0.3536f, 0.3536f, -0.3536f, -0.3536f, 0.3536f},
      {0.2778f, -0.4904f, 0.0975f, 0.4157f, -0.4157f, -0.0975f, 0.4904f, -0.2778f},
      {0.1913f, -0.4619f, 0.4619f, -0.1913f, -0.1913f, 0.4619f, -0.4619f, 0.1913f},
      {0.0975f, -0.2778f, 0.4157f, -0.4904f, 0.4904f, -0.4157f, 0.2778f, -0.0975f}};
  // endregion

  private void huffmanTables() {
    DC0MAP.keySet().forEach(key -> fillHuffmanTrees(dc0Tree, key, DC0MAP.get(key)));
    DC1MAP.keySet().forEach(key -> fillHuffmanTrees(dc1Tree, key, DC1MAP.get(key)));
    AC0MAP.keySet().forEach(key -> fillHuffmanTrees(ac0Tree, key, AC0MAP.get(key)));
    AC1MAP.keySet().forEach(key -> fillHuffmanTrees(ac1Tree, key, AC1MAP.get(key)));
  }

  private void fillHuffmanTrees(BinaryTree tree, String key, String bits) {
    Node current = tree.getRoot();
    Node aux;
    for (int i = 0; i < bits.length(); ++i) {
      if (i != bits.length() - 1) {
        if (bits.charAt(i) == '0') {
          aux = tree.getNodeLeft(current);
          if (aux == null) {
            tree.createNodeLeft(current);
            aux = tree.getNodeLeft(current);
          }
          current = aux;
        } else {
          aux = tree.getNodeRight(current);
          if (aux == null) {
            tree.createNodeRight(current);
            aux = tree.getNodeRight(current);
          }
          current = aux;
        }
      } else {
        if (bits.charAt(i) == '0') {
          tree.createNodeLeft(current, key);
        } else {
          tree.createNodeRight(current, key);
        }
      }
    }
  }

  private void marchThroughImage(byte[] image) throws IOException {
    version = String.valueOf((char) image[0]) + String.valueOf((char) image[1]);
    boolean width = false;
    boolean comment = false;
    boolean height = false;
    char c;
    w = 0;
    h = 0;
    int i;

    for (i = 3; !height; ++i) {
      c = (char) image[i];
      if (comment) {
        if (c == '\n') {
          comment = false;
        }
      } else if (!width) {
        if (c == '#') {
          comment = true;
        } else {
          if (w != 0 && (c == ' ' || c == '\n')) {
            width = true;
          } else {
            w = w * 10 + (c - '0');
          }
        }
      } else {
        if (c == '#') {
          comment = true;
        } else {
          if (c == '\n' || c == ' ') {
            height = true;
          } else {
            h = h * 10 + (c - '0');
          }
        }
      }
    }
    output += version + " " + w + " " + h + " ";
    out.write(output.getBytes());
    output = "";

    int header = image.length - w * h * 3;
    byte[] rgb = Arrays.copyOfRange(image, header, image.length);

    int[][] matrixR = new int[h][w];
    int[][] matrixG = new int[h][w];
    int[][] matrixB = new int[h][w];

    int k = 0;
    for (i = 0; i < h; ++i) {
      for (int j = 0; j < w; ++j) {
        if (k < rgb.length) {
          matrixR[i][j] = rgb[k] & 0xFF;
          matrixG[i][j] = rgb[k + 1] & 0xFF;
          matrixB[i][j] = rgb[k + 2] & 0xFF;
          k += 3;
        }
      }
    }

    double[][] matrixY = new double[h][w];
    double[][] matrixCb = new double[h][w];
    double[][] matrixCr = new double[h][w];

    //RGB to YCbCr
    for (i = 0; i < h; ++i) {
      for (int j = 0; j < w; ++j) {
        matrixY[i][j] = Math.max(0, Math.min(255,
            (0 + (0.299 * matrixR[i][j]) + (0.587 * matrixG[i][j]) + (0.114 * matrixB[i][j]))))
            - 128;
        matrixCb[i][j] = Math.max(0, Math.min(255,
            (128 - (0.168736 * matrixR[i][j]) - (0.331264 * matrixG[i][j]) + (0.5
                * matrixB[i][j])))) - 128;
        matrixCr[i][j] = Math.max(0, Math.min(255,
            (128 + (0.5 * matrixR[i][j]) - (0.418688 * matrixG[i][j]) + (0.081312
                * matrixB[i][j])))) - 128;
      }
    }

    double[][] auxy = new double[8][8];
    double[][] auxcb = new double[8][8];
    double[][] auxcr = new double[8][8];
    boolean end = false;
    int jj = 0;
    int ii = 0;

    while (!end) {
      for (i = 0; i < 8; ++i) { //subarrays de 8*8
        System.arraycopy(matrixY[ii + i], jj, auxy[i], 0, 8);
        System.arraycopy(matrixCb[ii + i], jj, auxcb[i], 0, 8);
        System.arraycopy(matrixCr[ii + i], jj, auxcr[i], 0, 8);
      }
      //dct transform + quantization
      dctTransform(auxy, true);
      dctTransform(auxcb, false);
      dctTransform(auxcr, false);

      if (jj == w - 8 && ii != h) {
        ii += 8;
        jj = 0;
      } else {
        jj += 8;
      }
      if (ii == h) {
        end = true;
      }
    }
    out.close();
  }

  private void dctTransform(double[][] matrix, boolean y) throws IOException {
        /*matrix = new double[][] {{-76,-73,-67,-62,-58,-67,-64,-55},{-65,-69,-73,-38,-19,-43,-59,-56},{-66,-69,-60,-15,16,-24,-62,-55},{-65,-70,-57,-6,26,-22,-58,-59},
                {-61,-67,-60,-24,-2,-40,-60,-58},{-49,-63,-68,-58,-51,-60,-70,-53},{-43,-57,-64,-69,-73,-67,-63,-45},{-41,-49,-59,-60,-63,-52,-50,-34}};
        */
    double[][] aux = new double[8][8];

    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        aux[i][j] = 0;
        for (int k = 0; k < 8; ++k) {
          aux[i][j] += dct[i][k] * matrix[k][j];
        }
      }
    }

    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        matrix[i][j] = 0;
        for (int k = 0; k < 8; ++k) {
          matrix[i][j] += aux[i][k] * dct[j][k];
        }
      }
    }

    quantization(matrix, y);
  }

  private void quantization(double[][] m, boolean y) throws IOException {
    int[][] qt;
    if (y) {
      qt = qtY;
    } else {
      qt = qtC;
    }

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        m[i][j] = m[i][j] / qt[i][j];
        m[i][j] = Math.round(m[i][j]);
      }
    }
    zigzag(m, y);
  }

  private void zigzag(double[][] m, boolean y) throws IOException {
    ArrayList<Integer> zigzag = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < 8 && j < 8) {
      if ((i + j) % 2 == 0) {
        if (i == 0) {
          zigzag.add((int) m[i][j]);
          if (j == 7) {
            ++i;
          } else {
            ++j;
          }
        } else if ((j == 7)) {
          zigzag.add((int) m[i][j]);
          ++i;
        } else if (i > 0) {
          zigzag.add((int) m[i][j]);
          --i;
          ++j;
        }
      } else {
        if (i == 7) {
          zigzag.add((int) m[i][j]);
          ++j;
        } else if (j == 0) {
          zigzag.add((int) m[i][j]);
          ++i;
        } else if (j > 0) {
          zigzag.add((int) m[i][j]);
          ++i;
          --j;
        }
      }
      if ((i == 7) && (j == 7)) {
        zigzag.add((int) m[i][j]);
        break;
      }
    }
    if (y) {
      encodeLum(zigzag);
    } else {
      encodeChro(zigzag);
    }
  }

  private void encodeLum(ArrayList<Integer> zigzag) throws IOException {
    boolean dc = false;
    int count = 0;
    int aux;
    for (int x : zigzag) {
      if (!dc) {
        dc = true;
        aux = x;
        int length = Integer.toBinaryString(abs(aux)).length();
        String key = "0" + Integer.toHexString(length).toUpperCase();
        output += DC0MAP.get(key);
        if (aux < 0) {
          aux = (int) (aux + Math.pow(2, length) - 1);
          int l = Integer.toBinaryString(aux).length();
          while (length-- - l != 0) {
            output += "0";
          }
        }
        output += Integer.toBinaryString(aux);
      } else {
        if (x == 0) {
          count++;
        } else {
          while (count > 15) {
            count -= 15;
            output += AC0MAP.get("F0");
          }
          String key = Integer.toHexString(count).toUpperCase();
          aux = x;
          int length = Integer.toBinaryString(abs(x)).length();
          key += Integer.toHexString(length).toUpperCase();
          output += AC0MAP.get(key);
          if (x < 0) {
            aux = (int) (x + Math.pow(2, length) - 1);
            int l = Integer.toBinaryString(aux).length();
            while (length-- - l != 0) {
              output += "0";
            }
          }
          output += Integer.toBinaryString(aux);
          count = 0;
        }
      }
    }
    output += AC0MAP.get("00");

    out.write(output.getBytes());
    output = "";
  }

  private void encodeChro(ArrayList<Integer> zigzag) throws IOException {
    boolean dc = false;
    int count = 0;
    int aux;
    for (int x : zigzag) {
      if (!dc) {
        dc = true;
        aux = x;
        int length = Integer.toBinaryString(abs(aux)).length();
        String key = "0" + Integer.toHexString(length).toUpperCase();
        output += DC1MAP.get(key);
        if (aux < 0) {
          aux = (int) (aux + Math.pow(2, length) - 1);
          int l = Integer.toBinaryString(aux).length();
          while (length-- - l != 0) {
            output += "0";
          }
        }
        output += Integer.toBinaryString(aux);
      } else {
        if (x == 0) {
          count++;
        } else {
          while (count > 15) {
            count -= 15;
            output += AC1MAP.get("F0");
          }
          String key = Integer.toHexString(count).toUpperCase();
          aux = x;
          int length = Integer.toBinaryString(abs(x)).length();
          key += Integer.toHexString(length).toUpperCase();
          output += AC1MAP.get(key);
          if (x < 0) {
            aux = (int) (x + Math.pow(2, length) - 1);
            int l = Integer.toBinaryString(aux).length();
            while (length-- - l != 0) {
              output += "0";
            }
          }
          output += Integer.toBinaryString(aux);
          count = 0;
        }
      }
    }
    output += AC1MAP.get("00");
    out.write(output.getBytes());
    output = "";
  }

  private void marchThroughSippy(String input) throws IOException {
    int i = 0;
    int value = 0;
    int id = 0;
    String bits = "";
    Node current;
    BinaryTree bt = dc0Tree;
    int num;
    int espais = 0;
    version = input.substring(0, input.indexOf(" "));
    input = input.substring(input.indexOf(" ") + 1);
    w = Integer.parseInt(input.substring(0, input.indexOf(" ")));
    input = input.substring(input.indexOf(" ") + 1);
    h = Integer.parseInt(input.substring(0, input.indexOf(" ")));
    input = input.substring(input.indexOf(" ") + 1);

    output += version + "\n" + w + " " + h + "\n" + "255" + "\n";
    out.write(output.getBytes());
    output = "";

    double[][] matrixR = new double[h][w];
    double[][] matrixG = new double[h][w];
    double[][] matrixB = new double[h][w];
    int jj = 0;
    int ii = 0;

    while (i < input.length()) {

      current = bt.getRoot();
      Queue<Integer> queue = new LinkedList<>();

      for (; i < input.length(); ++i) {
        if (!bt.isLeaf(current)) {
          if (input.charAt(i) == '0') {
            current = bt.getNodeLeft(current);
          } else {
            current = bt.getNodeRight(current);
          }
        } else {
          value = Integer.parseInt(current.getValue(), 16);
          bits = input.substring(i, i + value);
          i += value;
          break;
        }
      }
      num = Integer.parseInt(bits, 2);
      if (bits.charAt(0) == '0') {
        num -= (int) Math.pow(2, value) - 1;
      }

      queue.add(num);

      if (id == 0) {
        bt = ac0Tree;
      } else {
        bt = ac1Tree;
      }

      current = bt.getRoot();
      int zeros;
      int size;
      boolean eob = false;

      while (!eob) {
        if (!bt.isLeaf(current)) {
          if (input.charAt(i) == '0') {
            current = bt.getNodeLeft(current);
          } else {
            current = bt.getNodeRight(current);
          }
          i++;
        } else {
          zeros = Integer.parseInt(String.valueOf(current.getValue().charAt(0)), 16);
          size = Integer.parseInt(String.valueOf(current.getValue().charAt(1)), 16);
          if (zeros == 0 && size == 0) {
            eob = true;
          } else {
            while (zeros-- > 0) {
              queue.add(0);
            }
            if (size != 0) {
              bits = input.substring(i, i + size);
              num = getPositiveValue(bits, size);
              queue.add(getPositiveValue(bits, size));
            }
            i += size;
            current = bt.getRoot();
          }
        }
      }

      double[][] submatrix;
      submatrix = inverseZigzag(queue, id);

      if (id == 0) {
        for (int k = 0; k < 8; ++k) {
          System.arraycopy(submatrix[k], 0, matrixR[k + ii], jj, 8);
        }
        bt = dc1Tree;
        ++id;
      } else if (id == 1) {
        for (int k = 0; k < 8; ++k) {
          System.arraycopy(submatrix[k], 0, matrixG[k + ii], jj, 8);
        }
        bt = dc1Tree;
        ++id;
      } else {
        for (int k = 0; k < 8; ++k) {
          System.arraycopy(submatrix[k], 0, matrixB[k + ii], jj, 8);
        }
        bt = dc0Tree;
        id = 0;
        if (jj == w - 8) {
          ii += 8;
          jj = 0;
        } else {
          jj += 8;
        }
      }
    }
    writePPM(matrixR, matrixG, matrixB);
    out.close();
  }

  private int getPositiveValue(String bits, int value) {
    int num = Integer.parseInt(bits, 2);
    if (bits.charAt(0) == '0') {
      num -= (int) Math.pow(2, value) - 1;
    }
    return num;
  }

  private double[][] inverseZigzag(Queue<Integer> queue, int id) {
    double[][] matrix = new double[8][8];

    int i = 0;
    int j = 0;

    while (i < 8 && j < 8 && !queue.isEmpty()) {
      if ((i + j) % 2 == 0) {
        if (i == 0) {
          matrix[i][j] = queue.poll();
          if (j == 7) {
            ++i;
          } else {
            ++j;
          }
        } else if ((j == 7)) {
          matrix[i][j] = queue.poll();
          ++i;
        } else if (i > 0) {
          matrix[i][j] = queue.poll();
          --i;
          ++j;
        }
      } else {
        if (i == 7) {
          matrix[i][j] = queue.poll();
          ++j;
        } else if (j == 0) {
          matrix[i][j] = queue.poll();
          ++i;
        } else if (j > 0) {
          matrix[i][j] = queue.poll();
          ++i;
          --j;
        }
      }
      if ((i == 7) && (j == 7) && !queue.isEmpty()) {
        matrix[i][j] = queue.poll();
        break;
      }
    }

    return reverseQuantization(matrix, id);
  }

  private double[][] reverseQuantization(double[][] m, int id) {
        /*m = new double[][]{{-26,-3,-6,2,2,-1,0,0},{0,-2,-4,1,1,0,0,0},
                {-3,1,5,-1,-1,0,0,0},{-3,1,2,-1,0,0,0,0},
                {1,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}};*/
    int[][] qt;
    if (id == 0) {
      qt = qtY;
    } else {
      qt = qtC;
    }

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        m[i][j] = m[i][j] * qt[i][j];
        m[i][j] = Math.round(m[i][j]);
      }
    }
    return reverseDCT(m, id);
  }

  private double[][] reverseDCT(double[][] matrix, int id) {

    double[][] aux = new double[8][8];

    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        aux[i][j] = 0;
        for (int k = 0; k < 8; ++k) {
          aux[i][j] += dct[k][i] * matrix[k][j];
        }
      }
    }

    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        matrix[i][j] = 0;
        for (int k = 0; k < 8; ++k) {
          matrix[i][j] += aux[i][k] * dct[k][j];
        }
        matrix[i][j] = Math.round(matrix[i][j]) + 128;
        if (matrix[i][j] < 0) {
          matrix[i][j] = 0;
        } else if (matrix[i][j] > 255) {
          matrix[i][j] = 255;
        }
      }
    }
    return matrix;
  }

  private void writePPM(double[][] y, double[][] cb, double[][] cr) {
    int r;
    int g;
    int b;
    for (int i = 0; i < h; ++i) {
      for (int j = 0; j < w; ++j) {
        r = (int) Math.max(0, Math.min(255, (y[i][j] + 1.402 * (cr[i][j] - 128))));
        g = (int) Math.max(0,
            Math.min(255, (y[i][j] - 0.344136 * (cb[i][j] - 128) - 0.714136 * (cr[i][j] - 128))));
        b = (int) Math.max(0, Math.min(255, (y[i][j] + 1.772 * (cb[i][j] - 128))));
        out.write(r);
        out.write(g);
        out.write(b);
      }
    }
  }

  @Override
  public byte[] encode(byte[] input) {
    out = new ByteArrayOutputStream();
    try {
      huffmanTables();
      marchThroughImage(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }

  @Override
  public byte[] decode(byte[] input) {
    out = new ByteArrayOutputStream();
    String data = new String(input);
    try {
      huffmanTables();
      marchThroughSippy(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }

  @Override
  public Algorithm getAlgorithmUsed() {
    return Algorithm.JPEG;
  }
}
