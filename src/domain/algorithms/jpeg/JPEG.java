package domain.algorithms.jpeg;
//Author: Yaiza Cano

import domain.algorithms.base.BaseAlgorithm;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static java.lang.Math.abs;

public class JPEG implements BaseAlgorithm {

    private HashMap<String, String> dc0Map = new HashMap<String, String>();
    private HashMap<String, String> ac0Map = new HashMap<String, String>();
    private HashMap<String, String> dc1Map = new HashMap<String, String>();
    private HashMap<String, String> ac1Map = new HashMap<String, String>();
    private BinaryTree dc0Tree = new BinaryTree();
    private BinaryTree ac0Tree = new BinaryTree();
    private BinaryTree dc1Tree = new BinaryTree();
    private BinaryTree ac1Tree = new BinaryTree();
    private String output = "";
    private ByteArrayOutputStream out;
    private int lastDCY = 0;
    private int lastDCCb = 0;
    private int lastDCr = 0;
    private boolean isCb = true;
    private int[][] qtY = new int[][]{{16, 11, 10, 16, 24, 40, 51, 61}, {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56}, {14, 17, 22, 29, 51, 87, 80, 62}, {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92}, {49, 64, 78, 87, 103, 121, 120, 101}, {72, 92, 95, 98, 112, 110, 103, 99}};
    private int[][] qtC = new int[][]{{17, 18, 24, 47, 99, 99, 99, 99}, {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99}, {47, 66, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}};
    private float[][] dct = new float[][]{{0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f, 0.3536f},
            {0.4904f, 0.4157f, 0.2778f, 0.0975f, -0.0975f, -0.2778f, -0.4157f, -0.4904f},
            {0.4619f, 0.1913f, -0.1913f, -0.4619f, -0.4619f, -0.1913f, 0.1913f, 0.4619f},
            {0.4157f, -0.0975f, -0.4904f, -0.2778f, 0.2778f, 0.4904f, 0.0975f, -0.4157f},
            {0.3536f, -0.3536f, -0.3536f, 0.3536f, 0.3536f, -0.3536f, -0.3536f, 0.3536f},
            {0.2778f, -0.4904f, 0.0975f, 0.4157f, -0.4157f, -0.0975f, 0.4904f, -0.2778f},
            {0.1913f, -0.4619f, 0.4619f, -0.1913f, -0.1913f, 0.4619f, -0.4619f, 0.1913f},
            {0.0975f, -0.2778f, 0.4157f, -0.4904f, 0.4904f, -0.4157f, 0.2778f, -0.0975f}};
    private ArrayList<Integer> y = new ArrayList<>();
    private ArrayList<Integer> cb = new ArrayList<>();
    private ArrayList<Integer> cr = new ArrayList<>();

    //private  int z = 0;


    private void marchThroughSippy(String input) {
        int i = 0;
        int value = 0;
        String bits = "";
        Node current;
        int id = 0;
        BinaryTree bt = dc0Tree;
        int num;

        //System.out.println(input.length());
        //for(int j = 1204217; j < input.length(); ++j)System.out.println(input.charAt(j));

        while (i < input.length()) {

            current = bt.getRoot();
            Queue<Integer> queue = new LinkedList<>();

            //System.out.print("Bits: ");
//        System.out.println(("ID: " + id));
            for (; i < input.length(); ++i) {
                //System.out.print(" i: " + i + " char: " + input.charAt(i));
                if (!bt.isLeaf(current)) {
                    if (input.charAt(i) == '0') current = bt.getNodeLeft(current);
                    else current = bt.getNodeRight(current);
                } else {
                    value = Integer.parseInt(current.getValue(), 16);
                    //System.out.println("VAL: " + value);
                    bits = input.substring(i, i + value);
                    i += value;
                    break;
                }
            }
            //System.out.println();
            num = Integer.parseInt(bits, 2);
            if (bits.charAt(0) == '0') num -= (int) Math.pow(2, value) - 1;
            //System.out.println("Value " + value + " Num_ " + num + " bits: " + bits);
            /*switch (id) {
                case 0:
                    //System.out.println("Last: " + lastDCY);
                    num += lastDCY;
                    //System.out.println("mnumn" + num);
                    lastDCY = num;
                    //id++;
                    //bt = dc1Tree;
                    break;
                case 1:
                    //System.out.println("Last: " + lastDCCb);
                    num += lastDCCb;
                    //System.out.println("num: " + num);
                    lastDCCb = num;
                    //id++;
                    //bt = dc1Tree;
                    break;
                case 2:
                    //System.out.println("Last: " + lastDCr);
                    //System.out.println("num: " + num);
                    num += lastDCr;
                    lastDCr = num;
                    //id = 0;
                    //bt = dc0Tree;
                    break;
            }*/

            queue.add(num);

            if (id == 0) bt = ac0Tree;
            else bt = ac1Tree;

            current = bt.getRoot();
            //System.out.println("DCT : " + num + " bits " + bits + "Value: " + value);
            //System.out.println();

            //(ceros,size)(num)
            int zeros;
            int size;
            boolean eob = false;

            //System.out.print("Bits: ");

            while (!eob) {
                //System.out.println(" i: " + i + " c " + input.charAt(i));
                if (!bt.isLeaf(current)) {
                    if (input.charAt(i) == '0') current = bt.getNodeLeft(current);
                    else current = bt.getNodeRight(current);
                    i++;
                } else {
                    zeros = Integer.parseInt(String.valueOf(current.getValue().charAt(0)), 16);
                    size = Integer.parseInt(String.valueOf(current.getValue().charAt(1)), 16);
                    if (zeros == 0 && size == 0) eob = true;
                    else {
                        //System.out.println();
                        //System.out.println("if0: " + zeros + " " + size);
                        while (zeros-- > 0) queue.add(0);
                        if (size != 0) {
                            bits = input.substring(i, i + size);
                            num = tractaBits(bits, size);
                            queue.add(tractaBits(bits, size));
                            //System.out.println(" bits " + bits + " num " + num);
                        }
                        i += size;
                        current = bt.getRoot();
                    }
                }
            }

            //System.out.println();
            /*System.out.println("Queue: ");
            for (int e : queue) System.out.print(" " + e + " ");
            System.out.println();*/

            if (id != 2) {
                bt = dc1Tree;
                ++id;
            } else {
                bt = dc0Tree;
                id = 0;
            }


            inverseZigzag(queue, id);

            //System.out.println(i);
        }

        writePPM();
        /*int[][] mat = new int[8][8];
        for(int k = 0; k < 8; ++k) {
            for (int j = 0; j < 8; ++j) {
                if (!aux.isEmpty()) {
                    mat[k][j] = aux.get(0);
                    aux.remove(0);
                }
                else mat[k][j] = 0;
            }
        }*/
/*

        for (int k[] : mat) {
            System.out.println();
            for (int x : k) System.out.print(" " + x + " ");
        }*/


    }

    private int tractaBits(String bits, int value) {
        int num = Integer.parseInt(bits, 2);
        if (bits.charAt(0) == '0') num -= (int) Math.pow(2, value) - 1;
        return num;
    }

    private void huffmanTables() throws IOException {
        FileReader DC0 = new FileReader("DClum.txt");
        FileReader AC0 = new FileReader("AClum.txt");
        FileReader DC1 = new FileReader("DCcbcr.txt");
        FileReader AC1 = new FileReader("ACcbcr.txt");

        BufferedReader d0 = new BufferedReader(DC0);
        BufferedReader a0 = new BufferedReader(AC0);
        BufferedReader d1 = new BufferedReader(DC1);
        BufferedReader a1 = new BufferedReader(AC1);

        String dicc;
        while ((dicc = d0.readLine()) != null) {
            String[] s = dicc.split(" ");
            dc0Map.put(s[0], s[1]);
            omplirArbresHuffman(dc0Tree, s[0], s[1]);
        }
        while ((dicc = a0.readLine()) != null) {
            String[] s = dicc.split(" ");
            ac0Map.put(s[0], s[1]);
            omplirArbresHuffman(ac0Tree, s[0], s[1]);
        }
        while ((dicc = d1.readLine()) != null) {
            String[] s = dicc.split(" ");
            dc1Map.put(s[0], s[1]);
            omplirArbresHuffman(dc1Tree, s[0], s[1]);
        }
        while ((dicc = a1.readLine()) != null) {
            String[] s = dicc.split(" ");
            ac1Map.put(s[0], s[1]);
            omplirArbresHuffman(ac1Tree, s[0], s[1]);
        }
        //dc1Tree.write(dc1Tree.getRoot());
    }

    private void omplirArbresHuffman(BinaryTree tree, String key, String bits) {
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
                if (bits.charAt(i) == '0') tree.createNodeLeft(current, key);
                else tree.createNodeRight(current, key);
            }
        }
    }

    private void marchThroughImage(byte[] image) throws IOException {
        String version = String.valueOf((char) image[0]) + String.valueOf((char) image[1]);
        //System.out.println("version: " + version);
        boolean width = false;
        boolean comment = false;
        boolean height = false;
        char c;
        int w = 0;
        int h = 0;
        int i;

        for (i = 4; !height; ++i) {
            c = (char) image[i];
            if (comment) {
                if (c == '\n') comment = false;
            } else if (!width) {
                if (c == '#') comment = true;
                else {
                    if (c == ' ') width = true;
                    else w = w * 10 + (c - '0');
                }
            } else {
                if (c == '\n') height = true;
                else h = h * 10 + (c - '0');
            }
        }
        //System.out.println("width: " + w);
        //System.out.println("height: " + h);
        int header = image.length - w * h * 3;
        byte[] rgb = Arrays.copyOfRange(image, header, image.length);

        //for (int j = 0; j < 27; ++j) System.out.print(rgb[j] + " ");

        int[][] matrixR = new int[w][w];
        int[][] matrixG = new int[w][w];
        int[][] matrixB = new int[w][w];

        int k = 0;
        for (i = 0; i < w; ++i) {
            for (int j = 0; j < w; ++j) {
                if (k < rgb.length) {
                    matrixR[i][j] = rgb[k];
                    matrixG[i][j] = rgb[k + 1];
                    matrixB[i][j] = rgb[k + 2];
                    k += 3;
                } else {
                    matrixR[i][j] = 0;
                    matrixG[i][j] = 0;
                    matrixB[i][j] = 0;
                }
            }
        }

        double[][] matrixY = new double[w][w];
        double[][] matrixCb = new double[w][w];
        double[][] matrixCr = new double[w][w];

        //RGB to YCbCr
        for (i = 0; i < w; ++i) {
            for (int j = 0; j < w; ++j) {
                matrixY[i][j] = 0 + 0.299 * matrixR[i][j] + 0.587 * matrixG[i][j] + 0.144 * matrixB[i][j];
                matrixCb[i][j] = -0.168736 * matrixR[i][j] - 0.331264 * matrixG[i][j] + 0.5 * matrixB[i][j];
                matrixCr[i][j] = 0.5 * matrixR[i][j] - 0.418688 * matrixG[i][j] - 0.081312 * matrixB[i][j];
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

           /*for(i = 0; i < 8; ++i) { //guardamos el resultado de la dct transform de nuevo en la matriz YCbCr
                System.arraycopy(auxy[i], 0, matrixY[i + ii], jj, 8);
                System.arraycopy(auxcb[i], 0, matrixCb[i + ii], jj, 8);
                System.arraycopy(auxcr[i], 0, matrixCr[i + ii], jj, 8);
            }*/

            if (jj == w - 8) {
                ii += 8;
                jj = 0;
            } else jj += 8;
            if (ii == w) end = true;
            //System.out.println("I: " + ii + " J: " +jj);
        }

        out.close();

    }

    private void dctTransform(double[][] matrix, boolean y) throws IOException {
        /*double[][] matrix = {{-76,-73,-67,-62,-58,-67,-64,-55},{-65,-69,-73,-38,-19,-43,-59,-56},{-66,-69,-60,-15,16,-24,-62,-55},{-65,-70,-57,-6,26,-22,-58,-59},
               {-61,-67,-60,-24,-2,-40,-60,-58},{-49,-63,-68,-58,-51,-60,-70,-53},{-43,-57,-64,-69,-73,-67,-63,-45},{-41,-49,-59,-60,-63,-52,-50,-34}};*/
        double[][] aux = new double[8][8];

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                aux[i][j] = 0;
                for (int k = 0; k < 8; ++k) aux[i][j] += dct[i][k] * matrix[k][j];
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                matrix[i][j] = 0;
                for (int k = 0; k < 8; ++k) matrix[i][j] += aux[i][k] * dct[j][k];
            }
        }

        /*System.out.println("DCT");
        for(int i = 0; i < 8; ++i) {
            System.out.println();
            for(int j = 0; j < 8; ++j) System.out.print(" " + matrix[i][j] + " ");
        }*/


        quantization(matrix, y);
        //System.out.println("Z: " + z++);
    }

    private void quantization(double[][] m, boolean y) throws IOException {
        int[][] qt;
        if (y) qt = qtY;
        else qt = qtC;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                m[i][j] = m[i][j] / qt[i][j];
                m[i][j] = Math.round(m[i][j]);
            }
        }

        /*if (y) System.out.println("Lum");
        else System.out.println("CbCr");
        for(int i = 0; i < 8; ++i) {
            System.out.println();
            for(int j = 0; j < 8; ++j) System.out.print(m[i][j] + " ");
        }
        System.out.println();*/

        zigzag(m, y);
    }

    private void zigzag(double[][] m, boolean y) throws IOException {
        ArrayList<Integer> zigzag = new ArrayList<Integer>();

        int i = 0;
        int j = 0;

        while (i < 8 && j < 8) {
            if ((i + j) % 2 == 0) {
                if (i == 0) {
                    zigzag.add((int) m[i][j]);
                    if (j == 7) ++i;
                    else ++j;
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
        //++z;
        //System.out.println(z);
        //System.out.print("Zigzag A" + z + " ");
        //for(int x : zigzag) System.out.print(x + " ");
        //int a = 10;
        //System.out.println();
        //output += "A" + z +" ";
        if (y) encodeLum(zigzag);
        else encodeChro(zigzag);
    }

    private void encodeLum(ArrayList<Integer> zigzag) throws IOException {
        boolean dc = false;
        int count = 0;
        int aux;
        for (int x : zigzag) {
            if (!dc) {
                dc = true;
                //aux = x - lastDCY;
                aux = x;
                //System.out.print(x + " " + lastDCY + " " + aux + " ");
                //lastDCY = x;
                int length = Integer.toBinaryString(abs(aux)).length();
                String key = "0" + Integer.toHexString(length).toUpperCase();
                //System.out.print(key + " " + dc0Map.get(key));
                output += dc0Map.get(key);
                if (aux < 0) {
                    aux = (int) (aux + Math.pow(2, length) - 1);
                    int l = Integer.toBinaryString(aux).length();
                    while (length-- - l != 0) output += "0";
                }
                output += Integer.toBinaryString(aux);
                //System.out.println(" " + Integer.toBinaryString(aux));
            } else {
                if (x == 0) count++;
                else {
                    while (count > 15) {
                        count -= 15;
                        output += ac0Map.get("F0");
                    }
                    String key = Integer.toHexString(count).toUpperCase();
                    aux = x;
                    int length = Integer.toBinaryString(abs(x)).length();
                    key += Integer.toHexString(length).toUpperCase();
                    output += ac0Map.get(key);
                    if (x < 0) {
                        aux = (int) (x + Math.pow(2, length) - 1);
                        int l = Integer.toBinaryString(aux).length();
                        while (length-- - l != 0) output += "0";
                    }
                    output += Integer.toBinaryString(aux);
                    count = 0;
                }
            }
        }
        output += ac0Map.get("00");

        out.write(output.getBytes());
        output = "";
        // output += "FIY";
        //System.out.println(output);
    }

    private void encodeChro(ArrayList<Integer> zigzag) throws IOException {
        boolean dc = false;
        int count = 0;
        int aux;
        for (int x : zigzag) {
            if (!dc) {
                //System.out.print(" " + lastDCCb + " " + lastDCr + " ");
                dc = true;
                /*if (isCb) {
                    aux = x - lastDCCb;
                    lastDCCb = x;
                    isCb = false;
                }
                else {
                    aux = x - lastDCr;
                    lastDCr = x;
                    isCb = true;
                }*/
                aux = x;
                int length = Integer.toBinaryString(abs(aux)).length();
                //System.out.print(x +  " " + aux + " " );
                String key = "0" + Integer.toHexString(length).toUpperCase();
                //System.out.print(key + " " + dc1Map.get(key));
                output += dc1Map.get(key);
                if (aux < 0) {
                    aux = (int) (aux + Math.pow(2, length) - 1);
                    int l = Integer.toBinaryString(aux).length();
                    while (length-- - l != 0) output += "0";
                }
                output += Integer.toBinaryString(aux);
                //System.out.println("aux: "+ aux +" Binary: " + Integer.toBinaryString(aux));
            } else {
                if (x == 0) count++;
                else {
                    while (count > 15) {
                        count -= 15;
                        output += ac1Map.get("F0");
                    }
                    //System.out.print(x +  " " + count + " " );
                    String key = Integer.toHexString(count).toUpperCase();
                    aux = x;
                    int length = Integer.toBinaryString(abs(x)).length();
                    key += Integer.toHexString(length).toUpperCase();
                    //System.out.print(key + " " + ac1Map.get(key));
                    output += ac1Map.get(key);
                    if (x < 0) {
                        aux = (int) (x + Math.pow(2, length) - 1);
                        int l = Integer.toBinaryString(aux).length();
                        while (length-- - l != 0) output += "0";
                    }
                    output += Integer.toBinaryString(aux);
                    //System.out.println("aux: "+ aux +" Binary: " + Integer.toBinaryString(aux));
                    count = 0;
                }
            }
        }
        output += ac1Map.get("00");
        //output += "Fi";
        out.write(output.getBytes());
        output = "";
        //output += "FIC";
        //System.out.println(output);
    }

    private void inverseZigzag(Queue<Integer> queue, int id) {
        double[][] matrix = new double[8][8];

        int i = 0;
        int j = 0;

        while (i < 8 && j < 8 && !queue.isEmpty()) {
            if ((i + j) % 2 == 0) {
                if (i == 0) {
                    matrix[i][j] = queue.poll();
                    if (j == 7) ++i;
                    else ++j;
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

        /*System.out.println("Zigzag");
        for(i = 0; i < 8; ++i) {
            System.out.println();
            for(j = 0; j < 8; ++j) System.out.print(" " + matrix[i][j] + " ");
        }*/

        reverseQuantization(matrix, id);
    }

    private void reverseQuantization(double[][] m, int id) {
        /*double[][] m = new double[][]{{-26,-3,-6,2,2,-1,0,0},{0,-2,-4,1,1,0,0,0},
                {-3,1,5,-1,-1,0,0,0},{-3,1,2,-1,0,0,0,0},
                {1,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}};*/

        int[][] qt;
        if (id == 0) qt = qtY;
        else qt = qtC;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                m[i][j] = m[i][j] * qt[i][j];
                m[i][j] = Math.round(m[i][j]);
            }
        }

        /*System.out.println("Quantization");
        for(int i = 0; i < 8; ++i) {
            System.out.println();
            for(int j = 0; j < 8; ++j) System.out.print(" " + m[i][j] + " ");
        }*/

        reverseDCT(m, id);
    }

    private void reverseDCT(double[][] matrix, int id) //no funciona bé
    {

        double[][] aux = new double[8][8];

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                aux[i][j] = 0;
                for (int k = 0; k < 8; ++k) aux[i][j] += dct[k][i] * matrix[k][j];
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                matrix[i][j] = 0;
                for (int k = 0; k < 8; ++k) {
                    matrix[i][j] += aux[i][k] * dct[k][j];
                }
                matrix[i][j] = Math.round(matrix[i][j]) + 128;
                if (matrix[i][j] < 0) matrix[i][j] = 0;
                else if (matrix[i][j] > 255) matrix[i][j] = 255;
            }
        }
       /* dct = new double[][]{{-66, -63, -71, -68, -56, -65, -68, -46},
                {-71, -73, -72, -46, -20, -41, -66, -57},
                {-70, -78, -68, -17, 20, -14, -61, -63},
                {-63, -73, -62, -8, 27, -14, -60, -58},
                {-58, -65, -61, -27, -6, -40, -68, -50},
                {-57, -57, -64, -58, -48, -66, -72, -47},
                {-53, -46, -61, -74, -65, -63, -62, -45},
                {-47, -34, -53, -74, -60, -47, -47, -41}};*/
        /*System.out.println("DCT");
        for(int i = 0; i < 8; ++i) {
            System.out.println();
            for(int j = 0; j < 8; ++j) System.out.print(" " + matrix[i][j] + " ");
        }*/

        ArrayList<Integer> color;
        if (id == 0) color = y;
        else if (id == 1) color = cb;
        else color = cr;

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                color.add((int) matrix[i][j]);
            }
        }

        //for (int x : color) System.out.print(" " + x);
    }

    private void writePPM() {
        int r;
        int g;
        int b;
        for (int i = 0; i < y.size(); ++i) {
            r = (int) (y.get(i) + 1.402 * (cr.get(i) - 128));
            g = (int) (y.get(i) - 0.344136 * (cb.get(i) - 128) - 0.714136 * (cr.get(i) - 128));
            b = (int) (y.get(i) + 1.722 * (cb.get(i) - 128));
            out.write(r);
            out.write(g);
            out.write(b);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        marchThroughSippy(data);
        return out.toByteArray();
    }
}