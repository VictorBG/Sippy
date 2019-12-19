package prop.algorithms;

import org.junit.Ignore;
import org.junit.Test;
import prop.algorithms.base.BaseAlgorithmTest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sergio Vazquez
 * <p>
 * DO NOT MODIFY, they work on linux but not on Windows!
 */
public class LZSSTest extends BaseAlgorithmTest {

  @Ignore
  @Test
  public void testLZW() {
    test(Algorithm.LZSS.getAlgorithm());
  }

  @Override
  public List<byte[]> getInputs() {
    return new ArrayList<byte[]>() {
      {
        add("D0N QuijoT3 DE LA MaNCh4".getBytes());
        add("abcdefghijklmnopqrstuvwxyz!_$%&/()=?^|@#~][{}-,:;€"
            .getBytes(StandardCharsets.US_ASCII));
        add("áéíóúàèìòùäëïöü".getBytes(StandardCharsets.US_ASCII));
      }
    };
  }

  @Override
  public List<byte[]> getOutputs() {
    return new ArrayList<byte[]>() {
      {
        add(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0x00, (byte) 0x00,
            (byte) 0x80, (byte) 0x44, (byte) 0x30, (byte) 0x4e, (byte) 0x20, (byte) 0x51,
            (byte) 0x75, (byte) 0x69, (byte) 0x6a, (byte) 0x6f, (byte) 0x54, (byte) 0x33,
            (byte) 0x20, (byte) 0x44, (byte) 0x45, (byte) 0x20, (byte) 0x4c, (byte) 0x41,
            (byte) 0x20, (byte) 0x4d, (byte) 0x61, (byte) 0x4e, (byte) 0x43, (byte) 0x68}

        );
        add(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x61,
            (byte) 0x62, (byte) 0x63, (byte) 0x64, (byte) 0x65, (byte) 0x66, (byte) 0x67,
            (byte) 0x68, (byte) 0x69, (byte) 0x6a, (byte) 0x6b, (byte) 0x6c, (byte) 0x6d,
            (byte) 0x6e, (byte) 0x6f, (byte) 0x70, (byte) 0x71, (byte) 0x72, (byte) 0x73,
            (byte) 0x74, (byte) 0x75, (byte) 0x76, (byte) 0x77, (byte) 0x78, (byte) 0x79,
            (byte) 0x7a, (byte) 0x21, (byte) 0x5f, (byte) 0x24, (byte) 0x25, (byte) 0x26,
            (byte) 0x2f, (byte) 0x28, (byte) 0x29, (byte) 0x3d, (byte) 0x3f, (byte) 0x5e,
            (byte) 0x7c, (byte) 0x40, (byte) 0x23, (byte) 0x7e, (byte) 0x5d, (byte) 0x5b,
            (byte) 0x7b, (byte) 0x7d, (byte) 0x2d, (byte) 0x2c, (byte) 0x3a, (byte) 0x3b}

        );
        add(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x18, (byte) 0x01,
            (byte) 0x3f, (byte) 0x3f, (byte) 0x3f, (byte) 0x00, (byte) 0x03, (byte) 0x00,
            (byte) 0x06, (byte) 0x3f, (byte) 0x3f, (byte) 0x3f}

        );
      }
    };
  }
}
