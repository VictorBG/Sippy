package domain;

import com.sun.istack.internal.NotNull;
import data.DataController;
import data.FactoryData;
import java.io.IOException;
import model.Statistics;
import model.compressed.ItemC;
import model.uncompressed.ItemNC;

public class Decompress extends Transaction<Statistics> {

  private String path;

  public Decompress(@NotNull String path) {
    this.path = path;
  }

  @Override
  public void execute() {
    try {

      DataController dataController = FactoryData.getInstance().getDataController();
      ItemC item = dataController.getCompressedItem(path);

      Statistics statistics = Statistics.create(item);
      ItemNC compressed = item.uncompress();
      statistics.stopTimer(compressed);

      dataController.saveFile(compressed);

      setResult(statistics);
    } catch (IOException ignore) {
    }
  }
}
