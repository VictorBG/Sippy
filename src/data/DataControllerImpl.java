package data;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import model.Item;
import model.compressed.ItemC;
import model.uncompressed.Folder;
import model.uncompressed.ItemNC;
import model.uncompressed.file.FilePpm;
import model.uncompressed.file.FileTxt;
import utils.FileUtils;

public class DataControllerImpl implements DataController {

  @Override
  public ItemNC getNonCompressedItem(String path) throws IOException {
    File file = new File(path);
    if (file.isDirectory()) {
      Folder folder = new Folder(path);
      for (File f : Objects.requireNonNull(file.listFiles())) {
        folder.addItem(getNonCompressedItem(f.getPath()));
      }
      return folder;
    } else if (file.isFile()) {
      return getSingleCompressedItem(path);
    } else {
      throw new UnsupportedEncodingException("The path is not accepted");
    }
  }

  @Override
  public ItemC getCompressedItem(String path) throws IOException {
    File file = new File(path);

    if (file.isFile() && "sippy".equals(FileUtils.getFileExtension(path))) {
      return new ItemC(path, FileUtils.readFile(file));
    }

    throw new UnsupportedEncodingException();
  }

  @Override
  public void saveCompress(byte[] data, String path) {
    internalSave(data, FileUtils.changeExtension(path, "sippy"));
  }

  @Override
  public void saveUncompress(byte[] data, String path) {
    internalSave(data, path);
  }

  private void internalSave(byte[] data, String path) {
    FileUtils.save(data, path);
  }


  private ItemNC getSingleCompressedItem(String path) throws IOException {
    switch (FileUtils.getFileExtension(path)) {
      case "ppm":
        return new FilePpm(path, FileUtils.readFile(new File(path)));
      case "txt":
        return new FileTxt(path, FileUtils.readFile(new File(path)));
      default:
        throw new UnsupportedEncodingException();
    }
  }
}
