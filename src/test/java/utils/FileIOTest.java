package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FileIOTest {

  private static final String INITIAL_FILE_PATH_1 = "C:\\Test\\test.xml";
  private static final String INITIAL_FILE_PATH_2 = "C:\\Test\\test.test.png";
  private static final String INITIAL_FILE_PATH_3 = "C:\\Test\\test.test.test.txt";

  private static final String OUTPUT_FILE_PATH_1 = "C:\\Test\\test.sippy";
  private static final String OUTPUT_FILE_PATH_2 = "C:\\Test\\test.test.sippy";
  private static final String OUTPUT_FILE_PATH_3 = "C:\\Test\\test.test.test.sippy";

  @Test
  void changeExtension() {
    assertEquals(FileIO.changeExtension(INITIAL_FILE_PATH_1, "sippy"), OUTPUT_FILE_PATH_1);
    assertEquals(FileIO.changeExtension(INITIAL_FILE_PATH_2, "sippy"), OUTPUT_FILE_PATH_2);
    assertEquals(FileIO.changeExtension(INITIAL_FILE_PATH_3, "sippy"), OUTPUT_FILE_PATH_3);
  }
}
