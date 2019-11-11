package domain.algorithms.base;

import java.util.List;
import org.junit.Assert;

/**
 * Author: Victor Blanco
 * <p>
 * Base implementation to test an algorithm.
 * <p>
 * A subclass must implement the {@link #getInputs()} and {@link #getOutputs()} methods, every
 * position of one list must corresponds to the same position of the other list, then when {@link
 * #test(BaseAlgorithm)} is called, the {@link BaseAlgorithm#decode(byte[])} and {@link
 * BaseAlgorithm#encode(byte[])} method of the {@link BaseAlgorithm} provided are tested based on
 * the values of the input and output lists.
 */
public abstract class BaseAlgorithmTest {

  protected abstract List<byte[]> getInputs();

  protected abstract List<byte[]> getOutputs();

  protected void test(BaseAlgorithm algorithm) {
    testEncode(algorithm);
    testDecode(algorithm);
  }

  private void testEncode(BaseAlgorithm algorithm) {

    List<byte[]> outputs = getOutputs();

    int[] index = {0};

    getInputs().forEach(i -> Assert.assertArrayEquals(
        algorithm.encode(i),
        outputs.get(index[0]++)));
  }

  private void testDecode(BaseAlgorithm algorithm) {
    List<byte[]> inputs = getInputs();

    int[] index = {0};

    getOutputs().forEach(i -> Assert.assertArrayEquals(
        algorithm.decode(i),
        inputs.get(index[0]++)));
  }
}
