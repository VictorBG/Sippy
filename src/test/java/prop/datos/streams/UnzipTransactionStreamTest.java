package prop.datos.streams;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import prop.algorithms.Algorithm;
import prop.datos.streams.impl.UnzipStreamImpl;
import prop.datos.streams.impl.ZipStreamImpl;

public class UnzipTransactionStreamTest {

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private File file1;
  private File file2;

  @Before
  public void setUp() throws Exception {
    /*
    A dummy sippy file is created in order to get the path to the file that later will
    be (or it is supposed to be) generated by the ZipStream
     */
    testFolder.create();
    file2 = testFolder.newFile("b.txt");
    File sippy = testFolder.newFile("b.sippy");
    String sippyPath = sippy.getAbsolutePath();
    Files.delete(sippy.toPath());
    file2.setWritable(true);

    FileOutputStream fileOutputStream = new FileOutputStream(file2);
    fileOutputStream.write("test".getBytes());

    new ZipStreamImpl(file2.getAbsolutePath(), file2.getAbsolutePath())
        .compressFile(file2.getAbsolutePath(), Algorithm.LZW);

    file1 = new File(sippyPath);
  }

  @After
  public void tearDown() throws Exception {
    testFolder.delete();
  }

  @Test(expected = UnsupportedEncodingException.class)
  public void invalidExtension() throws IOException {
    new UnzipStreamImpl("a.txt", null);
  }

  @Test
  public void unzip() throws IOException {

    assertEquals(file2.length(), 4); // original size

    UnzipStream unzipStream = new UnzipStreamImpl(file1.getAbsolutePath(),
        testFolder.getRoot().getPath());
    unzipStream.unzip();

    assertEquals(file2.length(), 4); // size after unzip
  }
}