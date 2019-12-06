package prop.dominio;

import prop.algorithms.Algorithm;
import prop.dominio.model.ItemNC;
import prop.dominio.model.Statistics;
import prop.dominio.model.uncompressed.File;
import prop.dominio.streams.ZipStream;
import java.io.IOException;

/**
 * @class Zip
 * @brief Zip transaction.
 * Author: Sergio Vazquez.
 * Creates a {@link ZipStream} and adds every item inside the {@link prop.dominio.model.ItemC} to the
 * stream, which will be encoded inside using the {@link Algorithm} provided (or a default if the
 * one provided is {@link Algorithm#AUTOMATIC}).
 * <p>
 * {@link ItemNC} is the responsible of the correct usage of the algorithm selected.
 * <p>
 * It also creates statistics of the operations and puts them to the result.
 */
public class Zip extends Transaction<Statistics> {

  private ItemNC item;
  private Algorithm algorithm;

  /**
   * @brief Constructora
   *\pre item existeix, algorithm existeix
   * \post Es ccrea una instancia de Zip
   */
  public Zip(ItemNC item, Algorithm algorithm) {
    this.item = item;
    this.algorithm = algorithm;
  }

  /**
   * @brief Executa la transaccio
   *\pre cert
   * \post  Es creen les estadístiques i el ZipStream, al acabar s'atura el timer i es setejen
   *  els resultats
   */
  @Override
  public void execute() throws IOException {
    Statistics statistics = new Statistics(item.getSize());

    ZipStream zipOutputStream = ZipStream.create(item);

    for (File file : item.getItems()) {
      zipOutputStream.addFile(file.zipInfo(algorithm));
    }

    zipOutputStream.close();

    statistics.stopTimer();
    statistics.setFinalSize(zipOutputStream.getTotalSize());
    setResult(statistics);
  }
}
