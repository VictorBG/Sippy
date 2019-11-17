package algorithms;

import java.util.ArrayList;
import java.util.List;

import algorithms.base.BaseAlgorithmTest;
import org.junit.Test;

/**
 * Author: Miguel Angel Cabrera
 */
public class LZSSTest extends BaseAlgorithmTest {

  @Test
  public void testLZSS() {
    test(Algorithm.LZSS.getAlgorithm());
  }

  @Override
  public List<byte[]> getInputs() {
    return new ArrayList<byte[]>() {
      {
        add("D0N QuÍjoT3\nDE LA MaNCh4".getBytes());
        add("abcdefghijklmnopqrstuvwxyz!·$%&/()=?¿^|@#~][{}-.-,:;ªº".getBytes());
        add("a1b2c3d4e5\náàä".getBytes());
        add("áéíóúàèìòùäëïöü".getBytes());
      }
    };
  }

  @Override
  public List<byte[]> getOutputs() {
    return new ArrayList<byte[]>() {
      {
        add(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6D, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20,
            (byte) 0x44, (byte) 0x30, (byte) 0x4E, (byte) 0x20, (byte) 0x51, (byte) 0x75,
            (byte) 0xC3, (byte) 0x8D, (byte) 0x6A, (byte) 0x6F, (byte) 0x54, (byte) 0x33,
            (byte) 0x0A, (byte) 0x44, (byte) 0x45, (byte) 0x20, (byte) 0x4C, (byte) 0x41,
            (byte) 0x20, (byte) 0x4D, (byte) 0x61, (byte) 0x4E, (byte) 0x43, (byte) 0x68,
            (byte) 0x34, (byte) 0x61, (byte) 0x62, (byte) 0x63, (byte) 0x64, (byte) 0x65,
            (byte) 0x66, (byte) 0x67, (byte) 0x68, (byte) 0x69, (byte) 0x6A, (byte) 0x6B,
            (byte) 0x6C, (byte) 0x6D, (byte) 0x6E, (byte) 0x6F, (byte) 0x70, (byte) 0x71,
            (byte) 0x72, (byte) 0x73, (byte) 0x74, (byte) 0x75, (byte) 0x76, (byte) 0x77,
            (byte) 0x78, (byte) 0x79, (byte) 0x7A, (byte) 0x21, (byte) 0xC2, (byte) 0xB7,
            (byte) 0x24, (byte) 0x25, (byte) 0x26, (byte) 0x2F, (byte) 0x28, (byte) 0x29,
            (byte) 0x3D, (byte) 0x3F, (byte) 0xC2, (byte) 0xBF, (byte) 0x5E, (byte) 0x7C,
            (byte) 0x40, (byte) 0x23, (byte) 0x7E, (byte) 0x5D, (byte) 0x5B, (byte) 0x7B,
            (byte) 0x7D, (byte) 0x2D, (byte) 0x2E, (byte) 0x2D, (byte) 0x2C, (byte) 0x3A,
            (byte) 0x7D, (byte) 0x2D, (byte) 0x2E, (byte) 0x2D, (byte) 0x2C, (byte) 0x3A,
            (byte) 0x3B, (byte) 0xC2, (byte) 0xAA, (byte) 0xC2, (byte) 0xBA, (byte) 0x22,
            (byte) 0x61, (byte) 0x31, (byte) 0x62, (byte) 0x32, (byte) 0x63, (byte) 0x33,
            (byte) 0x64, (byte) 0x34, (byte) 0x65, (byte) 0x35, (byte) 0x0A, (byte) 0xC3,
            (byte) 0xA1, (byte) 0xC3, (byte) 0xA0, (byte) 0xC3, (byte) 0xA4, (byte) 0xC3,
            (byte) 0xA1, (byte) 0xC3, (byte) 0xA9, (byte) 0xC3, (byte) 0xAD, (byte) 0xC3,
            (byte) 0xB3, (byte) 0xC3, (byte) 0xBA, (byte) 0xC3, (byte) 0xA0, (byte) 0xC3,
            (byte) 0xA8, (byte) 0xC3, (byte) 0xAC, (byte) 0xC3, (byte) 0xB2, (byte) 0xC3,
            (byte) 0xB9, (byte) 0xC3, (byte) 0xA4, (byte) 0xC3, (byte) 0xAB, (byte) 0xC3,
            (byte) 0xAF, (byte) 0xC3, (byte) 0xB6, (byte) 0xC3, (byte) 0xBC, (byte) 0x0A}
        );
      }
    };
  }
}
