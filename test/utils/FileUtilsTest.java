package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileUtilsTest {

  private final static String DOT = ".";
  private final static String EXTENSION_1 = "txt";
  private final static String EXTENSION_2 = "ppm";

  private static String PATH_1 = "test/test";
  private static String PATH_1_SIPPY = "test/test";
  private static String PATH_2 = "test/test/test/test/test/test";
  private static String PATH_2_SIPPY = "test/test/test/test/test/test";
  private static String PATH_3 = "C:\\Users\\Prop\\Test";
  private static String PATH_3_SIPPY = "C:\\Users\\Prop\\Test";
  private static String PATH_4 = "C:\\Users\\Prop\\Test.sippy";
  private static String PATH_4_SIPPY = "C:\\Users\\Prop\\Test.sippy";

  static {
    PATH_1 += DOT + EXTENSION_1;
    PATH_1_SIPPY += DOT + FileUtils.DEFAULT_ENCODING_EXTENSION;
    PATH_2 += DOT + EXTENSION_2;
    PATH_2_SIPPY += DOT + FileUtils.DEFAULT_ENCODING_EXTENSION;
    PATH_3 += DOT + EXTENSION_2;
    PATH_3_SIPPY += DOT + FileUtils.DEFAULT_ENCODING_EXTENSION;
    PATH_4 += DOT + EXTENSION_1;
    PATH_4_SIPPY += DOT + FileUtils.DEFAULT_ENCODING_EXTENSION;
  }

  @Test
  public void changeExtension() {
    assertEquals(FileUtils.changeExtension(PATH_1, FileUtils.DEFAULT_ENCODING_EXTENSION),
        PATH_1_SIPPY);
    assertEquals(FileUtils.changeExtension(PATH_2, FileUtils.DEFAULT_ENCODING_EXTENSION),
        PATH_2_SIPPY);
    assertEquals(FileUtils.changeExtension(PATH_3, FileUtils.DEFAULT_ENCODING_EXTENSION),
        PATH_3_SIPPY);
    assertEquals(FileUtils.changeExtension(PATH_4, FileUtils.DEFAULT_ENCODING_EXTENSION),
        PATH_4_SIPPY);
  }

  @Test
  public void getFileExtension() {
    assertEquals(FileUtils.getFileExtension(PATH_1), EXTENSION_1);
    assertEquals(FileUtils.getFileExtension(PATH_2), EXTENSION_2);
    assertEquals(FileUtils.getFileExtension(PATH_3), EXTENSION_2);
    assertEquals(FileUtils.getFileExtension(PATH_4), EXTENSION_1);

    assertEquals(FileUtils.getFileExtension(PATH_1_SIPPY), FileUtils.DEFAULT_ENCODING_EXTENSION);
    assertEquals(FileUtils.getFileExtension(PATH_2_SIPPY), FileUtils.DEFAULT_ENCODING_EXTENSION);
    assertEquals(FileUtils.getFileExtension(PATH_3_SIPPY), FileUtils.DEFAULT_ENCODING_EXTENSION);
    assertEquals(FileUtils.getFileExtension(PATH_4_SIPPY), FileUtils.DEFAULT_ENCODING_EXTENSION);
  }
}
