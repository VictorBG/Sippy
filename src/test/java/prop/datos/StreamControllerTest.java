package prop.datos;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import prop.datos.controllers.StreamController;
import prop.datos.streams.UnzipStream;
import prop.datos.streams.ZipStream;

public class StreamControllerTest {

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private final StreamController streamController = DataFactory.getInstance().getStreamController();

  private File file1;
  private File file2;
  private File file3;
  private File file4;

  @Before
  public void setUp() throws Exception {
    testFolder.create();
    file1 = testFolder.newFile("a.txt");
    file2 = testFolder.newFile("b.txt");
    file3 = testFolder.newFile("a.sippy");
    file4 = testFolder.newFile("b.sippy");
  }

  @After
  public void tearDown() throws Exception {
    testFolder.delete();
  }

  @Test
  public void getZipStream() throws IOException {
    ZipStream zipStream = streamController.getZipStream(file1.getAbsolutePath());
    assertNotNull(zipStream);
    assertNotEquals(zipStream, streamController.getZipStream(file2.getAbsolutePath()));
  }

  @Test
  public void getUnzipStream() throws IOException {
    UnzipStream unzipStream = streamController.getUnzipStream(file3.getAbsolutePath());
    assertNotNull(unzipStream);
    assertNotEquals(unzipStream, streamController.getUnzipStream(file4.getAbsolutePath()));
  }

  @Test(expected = UnsupportedEncodingException.class)
  public void getUnzipStreamWithInvalidExtension() throws IOException {
    streamController.getUnzipStream("./a.txt");
  }

  @Test(expected = FileNotFoundException.class)
  public void getUnzipStreamWithInvalidPath() throws IOException {
    streamController.getUnzipStream("./c.sippy");
  }
}
