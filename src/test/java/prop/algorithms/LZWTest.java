package prop.algorithms;

import prop.algorithms.base.BaseAlgorithmTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Author: Sergio Vazquez
 *
 * TODO: Fix
 */
public class LZWTest extends BaseAlgorithmTest {

  @Test
  public void testLZW() {
    test(Algorithm.LZW.getAlgorithm());
  }

  @Override
  public List<byte[]> getInputs() {
    return new ArrayList<byte[]>() {
      {
        add("D0N QuijoT3 DE LA MaNCh4".getBytes());
        add("abcdefghijklmnopqrstuvwxyz!_$%&/()=?^|@#~][{}-,:;".getBytes());
        add("áéíóúàèìòùäëïöü".getBytes());
      }
    };
  }

  @Override
  public List<byte[]> getOutputs() {
    return new ArrayList<byte[]>() {
      {
        add(new byte[]{(byte) 0x44, (byte) 0x00, (byte) 0x03, (byte) 0x4e, (byte) 0x00, (byte) 0x02,
            (byte) 0x51, (byte) 0x05, (byte) 0x07, (byte) 0x69, (byte) 0x0a, (byte) 0x06,
            (byte) 0x6f, (byte) 0x04, (byte) 0x05, (byte) 0x33, (byte) 0x00, (byte) 0x02,
            (byte) 0x44, (byte) 0x05, (byte) 0x04, (byte) 0x20, (byte) 0x0c, (byte) 0x04,
            (byte) 0x41, (byte) 0x00, (byte) 0x02, (byte) 0x4d, (byte) 0x01, (byte) 0x06,
            (byte) 0x4e, (byte) 0x03, (byte) 0x04, (byte) 0x68, (byte) 0x04, (byte) 0x03}
        );
        add(new byte[]{(byte) 0x61, (byte) 0x02, (byte) 0x06, (byte) 0x63, (byte) 0x04, (byte) 0x06,
            (byte) 0x65, (byte) 0x06, (byte) 0x06, (byte) 0x67, (byte) 0x08, (byte) 0x06,
            (byte) 0x69, (byte) 0x0a, (byte) 0x06, (byte) 0x6b, (byte) 0x0c, (byte) 0x06,
            (byte) 0x6d, (byte) 0x0e, (byte) 0x06, (byte) 0x6f, (byte) 0x00, (byte) 0x07,
            (byte) 0x71, (byte) 0x02, (byte) 0x07, (byte) 0x73, (byte) 0x04, (byte) 0x07,
            (byte) 0x75, (byte) 0x06, (byte) 0x07, (byte) 0x77, (byte) 0x08, (byte) 0x07,
            (byte) 0x79, (byte) 0x0a, (byte) 0x07, (byte) 0x21, (byte) 0x0f, (byte) 0x05,
            (byte) 0x24, (byte) 0x05, (byte) 0x02, (byte) 0x26, (byte) 0x0f, (byte) 0x02,
            (byte) 0x28, (byte) 0x09, (byte) 0x02, (byte) 0x3d, (byte) 0x0f, (byte) 0x03,
            (byte) 0x5e, (byte) 0x0c, (byte) 0x07, (byte) 0x40, (byte) 0x03, (byte) 0x02,
            (byte) 0x7e, (byte) 0x0d, (byte) 0x05, (byte) 0x5b, (byte) 0x0b, (byte) 0x07,
            (byte) 0x7d, (byte) 0x0d, (byte) 0x02, (byte) 0x2c, (byte) 0x0a, (byte) 0x03,
            (byte) 0x3b, (byte) 0x00}
        );
        add(new byte[]{(byte) 0xc3, (byte) 0x01, (byte) 0x0a, (byte) 0xc3, (byte) 0x09, (byte) 0x0a,
            (byte) 0xc3, (byte) 0x0d, (byte) 0x0a, (byte) 0xc3, (byte) 0x03, (byte) 0x0b,
            (byte) 0xc3, (byte) 0x0a, (byte) 0x0b, (byte) 0xc3, (byte) 0x00, (byte) 0x0a,
            (byte) 0xc3, (byte) 0x08, (byte) 0x0a, (byte) 0xc3, (byte) 0x0c, (byte) 0x0a,
            (byte) 0xc3, (byte) 0x02, (byte) 0x0b, (byte) 0xc3, (byte) 0x09, (byte) 0x0b,
            (byte) 0xc3, (byte) 0x04, (byte) 0x0a, (byte) 0xc3, (byte) 0x0b, (byte) 0x0a,
            (byte) 0xc3, (byte) 0x0f, (byte) 0x0a, (byte) 0xc3, (byte) 0x06, (byte) 0x0b,
            (byte) 0xc3, (byte) 0x0c, (byte) 0x0b}
        );
      }
    };
  }
}
