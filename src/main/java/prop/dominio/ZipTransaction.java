package prop.dominio;

import java.io.IOException;
import prop.algorithms.Algorithm;
import prop.datos.DataFactory;
import prop.datos.streams.ZipStream;
import prop.dominio.mappers.ItemMapper;
import prop.dominio.model.ItemNC;
import prop.dominio.model.Statistics;
import prop.dominio.model.uncompressed.File;

/**
 * Author: Sergio Vazquez.
 *
 * @class Zip
 * @brief Creates a {@link ZipStream} and adds every item inside the {@link prop.dominio.model.ItemC}
 *     to the stream, which will be encoded inside using the {@link Algorithm} provided
 *     (or a default if the one provided is {@link Algorithm#AUTOMATIC}).
 *     {@link ItemNC} is the responsible of the correct usage of the algorithm selected.
 *     It also creates statistics of the operations and puts them to the result.
 */
public class ZipTransaction extends Transaction<Statistics> {

  private String path;
  private String outputPath;
  private Algorithm algorithm;

  /**
   * @brief Constructora
   *     \pre item existeix, algorithm existeix
   *     \post Es ccrea una instancia de Zip
   */
  public ZipTransaction(String path, String outputPath, Algorithm algorithm) {
    this.path = path;
    this.outputPath = outputPath;
    this.algorithm = algorithm;
  }

  /**
   * @brief Executa la transaccio
   *     \pre cert
   *     \post  Es creen les estadístiques i el ZipStream, al acabar s'atura el timer i es setejen
   *     els resultats
   */
  @Override
  public void execute() throws IOException {
    DataFactory factory = DataFactory.getInstance();
    ItemNC item = ItemMapper.mapItem(factory.getFilesController().getFile(path));

    Statistics statistics = new Statistics(item.getSize());

    ZipStream zipStream = factory.getStreamController().getZipStream(path, outputPath);

    for (File file : item.getItems()) {
      zipStream.compressFile(file.getPath(), file.getSupportedAlgorithm(algorithm));
    }

    statistics.stopTimer();
    statistics.setFinalSize(zipStream.getTotalSize());
    setResult(statistics);
  }
}
