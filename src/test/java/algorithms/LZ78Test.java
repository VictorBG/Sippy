package algorithms;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.base.BaseAlgorithmTest;

public class LZ78Test extends BaseAlgorithmTest<String, String> {

  @Test
  public void testLZ78() {
    testEncode(new LZ78());
  }

  @Override
  public List<String> getInputs() {
    return new ArrayList<String>() {
      {
        add("esto es un test");
        add("noespacio");
        add("salto\nde\nlinea");
      }
    };
  }

  @Override
  public List<String> getOutputs() {
    return new ArrayList<String>() {
      {
        add("{0=0:e, 1=0:s, 2=0:t, 3=0:o, 4=0: , 5=1:s, 6=5:u, 7=0:n, 8=5:t, 9=1:t}");
        add("{0=0:n, 1=0:o, 2=0:e, 3=0:s, 4=0:p, 5=0:a, 6=0:c, 7=0:i}");
        add("{0=0:s, 1=0:a, 2=0:l, 3=0:t, 4=0:o, 5=0:\n, 6=0:d, 7=0:e, 8=6:l, 9=0:i, 10=0:n, 11=8:a}");
      }
    };
  }
}
