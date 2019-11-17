package dominio.drivers;

import algorithms.Algorithm;

public class LZSSDriver extends BaseAlgorithmDriver {

  public static void main(String[] args) {
    new LZ78Driver();
  }

  @Override
  protected Algorithm getAlgorithm() {
    return Algorithm.LZSS;
  }
}
