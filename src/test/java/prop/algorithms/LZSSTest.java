package prop.algorithms;
import java.util.ArrayList;
import java.util.List;
import prop.algorithms.base.BaseAlgorithmTest;
import org.junit.Test;


/**
 * Author: Miguel Angel Cabrera
 */
public class LZSSTest extends BaseAlgorithmTest {

  @Test
  public void testLZSS() {
    testEncode(Algorithm.LZSS.getAlgorithm());
    testDecode(Algorithm.LZSS.getAlgorithm());
  }

  @Override
  public List<byte[]> getInputs() {
    return new ArrayList<byte[]>() {
      {
        add("Lorem ipsum dolor sit amet.".getBytes());
      }
    };
  }

  @Override
  public List<byte[]> getOutputs() {
    return new ArrayList<byte[]>() {
      {
        add(new byte[]{

                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1B, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08,
                        (byte) 0x4c, (byte) 0x6f, (byte) 0x72, (byte) 0x65, (byte) 0x6d, (byte) 0x20, (byte) 0x69, (byte) 0x70,
                        (byte) 0x73, (byte) 0x75, (byte) 0x6d, (byte) 0x20, (byte) 0x64, (byte) 0x6f, (byte) 0x6c, (byte) 0x6f,
                        (byte) 0x72, (byte) 0x20, (byte) 0x73, (byte) 0x69, (byte) 0x74, (byte) 0x20, (byte) 0x61, (byte) 0x6d,
                        (byte) 0x65, (byte) 0x74, (byte) 0x2e
                }
        );
      }
    };
  }
}