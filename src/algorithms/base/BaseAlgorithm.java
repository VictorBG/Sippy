package algorithms.base;

import java.util.Optional;

public interface BaseAlgorithm<Input, Output> {

  default Optional<Output> encode(Input input) {
    return Optional.empty();
  }

  default Optional<Input> decode(Output input) {
    return Optional.empty();
  }
}
