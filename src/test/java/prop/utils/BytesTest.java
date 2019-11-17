package prop.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class BytesTest {

  private static final byte[] B1 = new byte[]{1, 2, 3, 4};
  private static final byte[] B2 = new byte[]{4, 3, 2, 1};
  private static final byte[] B3 = new byte[]{2, 4, 6, 8};
  private static final byte[] B12 = new byte[]{1, 2, 3, 4, 4, 3, 2, 1};
  private static final byte[] B21 = new byte[]{4, 3, 2, 1, 1, 2, 3, 4,};
  private static final byte[] B23 = new byte[]{4, 3, 2, 1, 2, 4, 6, 8};
  private static final byte[] B32 = new byte[]{2, 4, 6, 8, 4, 3, 2, 1};
  private static final byte[] B13 = new byte[]{1, 2, 3, 4, 2, 4, 6, 8};
  private static final byte[] B31 = new byte[]{2, 4, 6, 8, 1, 2, 3, 4};


  @Test
  public void concat() {
    assertArrayEquals(Bytes.concat(B1, B2), B12);
    assertArrayEquals(Bytes.concat(B2, B1), B21);
    assertArrayEquals(Bytes.concat(B2, B3), B23);
    assertArrayEquals(Bytes.concat(B3, B2), B32);
    assertArrayEquals(Bytes.concat(B1, B3), B13);
    assertArrayEquals(Bytes.concat(B3, B1), B31);
  }
}
