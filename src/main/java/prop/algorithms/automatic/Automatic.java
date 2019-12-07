package prop.algorithms.automatic;

import java.util.Arrays;
import java.util.List;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

/**
 * Author: Victor Blanco
 */
public class Automatic implements BaseAlgorithm {

  private List<Algorithm> allowedAlgorithms;
  private Algorithm algorithmUsed;

  public void setAllowedAlgorithms(List<Algorithm> algorithms) {
    this.allowedAlgorithms = algorithms;
  }

  @Override
  public byte[] encode(byte[] input) {

    if (allowedAlgorithms == null) {
      return new byte[0];
    }

    Algorithm bestAlgorithm = allowedAlgorithms.get(0);
    int minimumSize = input.length;
    int initialSize = input.length;

    byte[] dataTest = Arrays.copyOf(input, Math.min(1024, initialSize));

    for (Algorithm algorithm : allowedAlgorithms) {
      int size;
      if ((size = algorithm.getAlgorithm().encode(dataTest).length) < minimumSize) {
        minimumSize = size;
        bestAlgorithm = algorithm;
      }
    }

    this.algorithmUsed = bestAlgorithm;
    return bestAlgorithm.getAlgorithm().encode(input);
  }

  @Override
  public byte[] decode(byte[] input) {
    return new byte[0];
  }

  @Override
  public Algorithm getAlgorithmUsed() {
    return algorithmUsed;
  }
}
