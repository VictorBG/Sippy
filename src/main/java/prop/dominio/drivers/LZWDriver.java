package prop.dominio.drivers;

import prop.algorithms.Algorithm;

public class LZWDriver extends BaseAlgorithmDriver {

  public static void main(String[] args) {
    new LZWDriver();
  }


  @Override
  protected Algorithm getAlgorithm() {
    return Algorithm.LZW;
  }
}
