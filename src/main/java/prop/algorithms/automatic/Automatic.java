package prop.algorithms.automatic;

import java.util.Arrays;
import java.util.List;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

/**
 * Author: Victor Blanco
 *
 * @class Automatic
 * @brief La clase no es usada ja que l'algorisme automatic esta "hardcodejat" al LZW per a txt
 *     per peticio del professor.
 *
 *     Automatic algorithm chooser that chooses the algorithm based on the
 *     best algorithm for the given input.
 *
 *     It iterates for all of the {@link #allowedAlgorithms} and encodes a small fragment of the
 *     data,
 *     which is 1KB if the file is larger than that or the whole file otherwise, then it returns
 *     the input encoded with the best algorithm based on the major percentage of encoding.
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
