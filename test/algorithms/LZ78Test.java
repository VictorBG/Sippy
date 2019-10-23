package algorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import algorithms.base.BaseAlgorithmTest;

public class LZ78Test extends BaseAlgorithmTest<byte[], byte[]> {

  @Test
  public void testLZ78() {
    testEncode(Algorithms.LZ78.getAlgorithm());
  }

  @Override
  public List<byte[]> getInputs() {
    return new ArrayList<byte[]>() {
      {
        add("esto es un test".getBytes());
        add("noespacio".getBytes());
        add("salto\nde\nlinea".getBytes());
      }
    };
  }

  @Override
  public List<byte[]> getOutputs() {
    return new ArrayList<byte[]>() {
      {
        add("{0=0:e, 1=0:s, 2=0:t, 3=0:o, 4=0: , 5=0:s, 6=4:u, 7=0:n, 8=4:t, 9=5:t}".getBytes());
        add("{0=0:n, 1=0:o, 2=0:e, 3=0:s, 4=0:p, 5=0:a, 6=0:c, 7=0:i}".getBytes());
        add("{0=0:s, 1=0:a, 2=0:l, 3=0:t, 4=0:o, 5=0:\n, 6=0:d, 7=0:e, 8=5:l, 9=0:i, 10=0:n, 11=7:a}"
            .getBytes());
      }
    };
  }
}
