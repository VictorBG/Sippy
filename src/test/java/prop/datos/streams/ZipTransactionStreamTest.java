package prop.datos.streams;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import prop.algorithms.Algorithm;
import prop.datos.streams.impl.ZipStreamImpl;
import prop.utils.Constants;
import prop.utils.FileUtils;

public class ZipTransactionStreamTest {

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private File file1;
  private File file2;

  @Before
  public void setUp() throws Exception {
    testFolder.create();
    file1 = testFolder.newFile("a.txt");
    file2 = testFolder.newFile("b.txt");
    file2.setWritable(true);

    FileOutputStream fileOutputStream = new FileOutputStream(file2);
    fileOutputStream.write("test".getBytes());
    fileOutputStream.close();
  }

  @After
  public void tearDown() throws Exception {
    testFolder.delete();
  }

  @Test
  public void create() throws IOException {
    ZipStream zipStream = new ZipStreamImpl(file1.getAbsolutePath(), file1.getCanonicalPath());

    File encodedFile = new File(
        FileUtils.changeExtension(file1.getAbsolutePath(), Constants.DEFAULT_ENCODING_EXTENSION));

    assertTrue(encodedFile.exists());

  }

  @Test
  public void compressFile() throws IOException {
    ZipStream zipStream = new ZipStreamImpl(file1.getAbsolutePath(), file1.getCanonicalPath());
    zipStream.compressFile(file2.getAbsolutePath(), Algorithm.LZW);
    File encodedFile = new File(
        FileUtils.changeExtension(file1.getAbsolutePath(), Constants.DEFAULT_ENCODING_EXTENSION));

    assertEquals(encodedFile.length(), 21);
  }
}
