package domain;

import algorithms.Algorithm;
import com.sun.istack.internal.NotNull;
import data.DataController;
import data.FactoryData;
import java.io.File;
import java.io.IOException;
import model.compressed.ItemC;
import model.uncompressed.ItemNC;
import utils.FileUtils;

public class Compress extends UseCase<Void> {

  private Algorithm algorithm;
  private String path;

  public Compress(@NotNull Algorithm algorithm, @NotNull String path) {
    this.algorithm = algorithm;
    this.path = path;
  }

  @Override
  public void execute() {
    try {
      // faltan las estadisticas
      byte[] file = FileUtils.readFile(new File(path));

      DataController dataController = FactoryData.getInstance().getDataController();
      ItemNC item = dataController.getNonCompressedItem(path);

      ItemC compressed;

      if (algorithm == Algorithm.AUTOMATIC) {
        compressed = item.compress();
      } else {
        compressed = item.compress(algorithm);
      }
      dataController.saveCompress(compressed.getData(), compressed.getPath());
    } catch (IOException ignore) {
    }
  }
}
