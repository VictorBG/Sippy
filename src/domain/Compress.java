package domain;

import algorithms.Algorithm;
import com.sun.istack.internal.NotNull;
import data.DataController;
import data.FactoryData;
import java.io.File;
import java.io.IOException;
import model.Statistics;
import model.compressed.ItemC;
import model.uncompressed.ItemNC;
import utils.FileUtils;

public class Compress extends Transaction<Statistics> {

  private Algorithm algorithm;
  private String path;

  public Compress(@NotNull Algorithm algorithm, @NotNull String path) {
    this.algorithm = algorithm;
    this.path = path;
  }

  @Override
  public void execute() {
    try {

      DataController dataController = FactoryData.getInstance().getDataController();
      ItemNC item = dataController.getNonCompressedItem(path);

      Statistics statistics = Statistics.create(item);
      ItemC compressed = item.compress(algorithm);
      statistics.stopTimer(compressed);

      dataController.saveFile(compressed);

      setResult(statistics);
    } catch (IOException ignore) {
    }
  }
}
