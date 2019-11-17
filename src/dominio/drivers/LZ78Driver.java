package dominio.drivers;

import algorithms.Algorithm;

public class LZ78Driver extends BaseAlgorithmDriver {

  public static void main(String[] args) {
    new LZ78Driver();
  }

  @Override
  protected Algorithm getAlgorithm() {
    return Algorithm.LZ78;
  }
}
