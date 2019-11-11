package domain.algorithms.base;

/**
 * Author: Victor Blanco
 * <p>
 * Base interface for the implementation of the encode/decode of an algorithm
 */
public interface BaseAlgorithm {

  byte[] encode(byte[] input);

  byte[] decode(byte[] input);
}
