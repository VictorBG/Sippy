package algorithms.base;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class BaseAlgorithmTest<I, O> {

  protected abstract List<I> getInputs();

  protected abstract List<O> getOutputs();

  protected void testEncode(BaseAlgorithm<I, O> algorithm) {
    List<I> inputs = getInputs();
    List<Optional<O>> outputs = getOutputs().stream().map(Optional::of)
        .collect(Collectors.toList());

    int[] index = {0};

    inputs.forEach(i -> assertEquals(algorithm.encode(i), outputs.get(index[0]++)));
  }

  protected void testDecode(BaseAlgorithm<I, O> algorithm) {
    List<O> outputs = getOutputs();
    List<Optional<I>> inputs = getInputs().stream().map(Optional::of).collect(Collectors.toList());

    int[] index = {0};

    outputs.forEach(o -> assertEquals(algorithm.decode(o), inputs.get(index[0]++)));
  }

}
