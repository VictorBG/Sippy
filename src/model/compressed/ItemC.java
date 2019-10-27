package model.compressed;

import algorithms.Algorithm;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import model.Item;
import model.uncompressed.ItemNC;
import utils.Bytes;

// Compressed item
public class ItemC extends Item {

  private byte[] originalData;
  private Algorithm algorithmUsed;

  public ItemC(String path) {
    super(path);
  }

  /**
   * Pre: data contains all the data, including algorithm used
   *
   * @param path
   * @param data
   */
  public ItemC(String path, byte[] data) {
    super(path, data);
    parseData();
  }

  public ItemC(String path, byte[] data, Algorithm a) {
    super(path, Bytes.concat(new byte[]{a.getId()}, data));
    algorithmUsed = a;
  }

  public Algorithm getAlgorithmUsed() {
    return algorithmUsed;
  }

  public void setAlgorithmUsed(Algorithm algorithmUsed) {
    this.algorithmUsed = algorithmUsed;
  }

  // TODO: Implement
  public ItemNC uncompress() {
    return null;
  }

  private void parseData() {
    algorithmUsed = Algorithm.valueOf(getData()[0]);
    originalData = Arrays.copyOfRange(getData(), 1, getData().length);
  }
}
