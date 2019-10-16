package algorithms.base;

import java.util.Optional;


public interface BaseAlgorithm<I, O>
{
    default Optional<O> encode(I input) {
        return Optional.empty();
    }

    default Optional<I> decode(O input) {
        return Optional.empty();
    }
}
