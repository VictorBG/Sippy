package model.compressed;

import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.FileUtils.DEFAULT_ENCODING_EXTENSION;

import algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import model.Item;
import model.uncompressed.File;
import model.uncompressed.Folder;
import model.uncompressed.ItemNC;
import utils.Bytes;
import utils.FileUtils;

// TODO: Doc
public class ItemC extends Item {

  private final static byte FOLDER_ID = 1;
  private final static byte FILE_ID = 0;

  private byte[] content;
  private Algorithm algorithmUsed;
  private boolean isFolder;

  private ItemC(String path) {
    super(path);
  }

  // TODO: This method name is pure shit
  public static ItemC createForFolder(String path, byte[] content) {
    ItemC itemC = new ItemC(path + DEFAULT_ENCODING_EXTENSION);
    itemC.setFolder(true);
    itemC.setData(Bytes.concat(
        new byte[]{FOLDER_ID},
        content
    ));
    itemC.setContent(content);
    return itemC;
  }

  // TODO: This method name is pure shit
  public static ItemC createForFile(String path, byte[] content, Algorithm algorithm) {
    ItemC itemC = new ItemC(FileUtils.changeExtension(path, DEFAULT_ENCODING_EXTENSION));
    itemC.setFolder(false);
    itemC.setAlgorithmUsed(algorithm);
    itemC.setData(Bytes.concat(
        new byte[]{FILE_ID},
        new byte[]{algorithm.getId()},
        FileUtils.getFileExtension(path).getBytes(UTF_8),
        content
    ));
    itemC.setContent(content);
    return itemC;
  }


  /**
   * TODO: This method name is pure shit
   *
   * @param path
   * @param data
   * @return
   */
  public static ItemC createFromFile(String path, byte[] data) {
    ItemC itemC = new ItemC(FileUtils.changeExtension(path, DEFAULT_ENCODING_EXTENSION));
    itemC.setFolder(data[0] == FOLDER_ID);
    itemC.setData(data);
    if (data[0] == FILE_ID) {
      itemC.setAlgorithmUsed(Algorithm.valueOf(data[1]));
      itemC.setContent(Arrays.copyOfRange(data, 5, data.length));
    } else if (data[0] == FOLDER_ID) {
      itemC.setContent(Arrays.copyOfRange(data, 1, data.length));
    }
    return itemC;
  }


  public Algorithm getAlgorithmUsed() {
    return algorithmUsed;
  }

  public void setAlgorithmUsed(Algorithm algorithmUsed) {
    this.algorithmUsed = algorithmUsed;
  }

  public boolean isFolder() {
    return isFolder;
  }

  public void setFolder(boolean folder) {
    isFolder = folder;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public ItemNC uncompress() throws UnsupportedEncodingException {
    ItemNC result;
    if (isFolder()) {
      // this is not yet implemented correctly
      result = new Folder(FileUtils.changeExtension(getPath(), ""));
    } else {
      String extension = new String(Arrays.copyOfRange(getData(), 2, getData().length));
      result = File.forExtension(extension, getPath(), getContent());
    }
    return result;
  }

}
