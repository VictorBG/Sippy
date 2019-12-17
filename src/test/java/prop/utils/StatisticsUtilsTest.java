package prop.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatisticsUtilsTest {

  @Test
  public void getTime() {

    assertEquals("131 ms", StatisticsUtils.getTime(0.131D));
    assertEquals("131 us", StatisticsUtils.getTime(0.000131D));
    assertEquals("1,31 ms", StatisticsUtils.getTime(0.00131D));
    assertEquals("1,31 ms", StatisticsUtils.getTime(0.001312D));
    assertEquals("131 s", StatisticsUtils.getTime(131D));
    assertEquals("131000000 s", StatisticsUtils.getTime(131000000D));
    assertEquals("13,1 ns", StatisticsUtils.getTime(0.0000000131D));
  }

  @Test
  public void getSize() {
    assertEquals("100 B", StatisticsUtils.getSize(100));
    assertEquals("0 B", StatisticsUtils.getSize(0));
    assertEquals("0 B", StatisticsUtils.getSize(-8000000));
    assertEquals("1 kB", StatisticsUtils.getSize(1025));
    assertEquals("1 MB", StatisticsUtils.getSize(Math.pow(2, 20) + 1));
    assertEquals("8 MB", StatisticsUtils.getSize(8 * Math.pow(2, 20)));
    assertEquals("17,6 MB", StatisticsUtils.getSize(17.57 * Math.pow(2, 20)));
    assertEquals("1 GB", StatisticsUtils.getSize(Math.pow(2, 30) + 1));
  }
}
