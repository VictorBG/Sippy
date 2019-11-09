package domain.algorithms.base;

import static org.junit.Assert.assertEquals;

import java.util.List;

public abstract class BaseAlgorithmTest<I, O> {

  protected abstract List<I> getInputs();

  protected abstract List<O> getOutputs();

//  protected void testEncode(BaseAlgorithm<I, O> algorithm) {
//    List<I> inputs = getInputs();
//    List<Optional<O>> outputs = getOutputs().stream().map(Optional::of)
//        .collect(Collectors.toList());
//
//    int[] index = {0};
//
//    inputs.forEach(i -> assertEquals(
//        algorithm.encode(i).get(),
//        outputs.get(index[0]++).get()));
//  }
//
//  protected void testDecode(BaseAlgorithm<I, O> algorithm) {
//    List<O> outputs = getOutputs();
//    List<Optional<I>> inputs = getInputs().stream().map(Optional::of).collect(Collectors.toList());
//
//    int[] index = {0};
//
//    outputs.forEach(o -> assertEquals(
//        algorithm.decode(o).get(),
//        inputs.get(index[0]++).get()));
//  }

}
