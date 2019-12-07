package prop.datos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import prop.datos.model.FolderBO;
import prop.datos.model.ItemBO;

public class FilesControllerTest {

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private final FilesController filesController = DataFactory.getInstance().getFilesController();

  private File invalidFile;
  private File validFile;
  private File folder;
  private File folderFile1;
  private File folderFile2;


  @Before
  public void setUp() throws Exception {
    testFolder.create();
    invalidFile = testFolder.newFile("a.a");
    validFile = testFolder.newFile("a.txt");
    folder = testFolder.newFolder("test");
    folderFile1 = testFolder.newFile("test/a.txt");
    folderFile2 = testFolder.newFile("test/b.txt");
  }

  @After
  public void tearDown() throws Exception {
    testFolder.delete();
  }

  @Test(expected = UnsupportedEncodingException.class)
  public void getUnsupportedFileExtension() throws IOException {
    filesController.getFile(invalidFile.getAbsolutePath());
  }

  @Test(expected = FileNotFoundException.class)
  public void getFileNotFound() throws IOException {
    filesController.getFile("./b.txt");
  }

  @Test
  public void getFile() throws IOException {
    ItemBO itemBO = filesController.getFile(validFile.getAbsolutePath());

    assertEquals(itemBO.getSize(), validFile.length());
    assertEquals(itemBO.getPath(), validFile.getAbsolutePath());
  }

  @Test
  public void getFolder() throws IOException {
    ItemBO itemBO = filesController.getFile(folder.getAbsolutePath());

    assertTrue(itemBO instanceof FolderBO);
    assertEquals(itemBO.getSize(), folder.length());
    assertEquals(itemBO.getPath(), folder.getAbsolutePath());

    FolderBO folderBO = (FolderBO) itemBO;

    ItemBO f1 = folderBO.getItems().get(0);
    ItemBO f2 = folderBO.getItems().get(1);

    assertEquals(f1.getSize(), folderFile1.length());
    assertEquals(f1.getPath(), folderFile1.getAbsolutePath());

    assertEquals(f2.getSize(), folderFile2.length());
    assertEquals(f2.getPath(), folderFile2.getAbsolutePath());
  }


}
