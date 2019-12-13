package prop.dominio;

import prop.datos.DataFactory;
import prop.dominio.mappers.ItemMapper;
import prop.dominio.model.ItemC;
import prop.dominio.model.Statistics;
import java.io.IOException;

/**
 * @class Unzip
 * @brief Unzip transaction.
 *     Author: Sergio Vazquez.
 *
 */
public class Unzip extends Transaction<Statistics> {

  private String path;

  /**
   * @brief Constructora
   *     \pre item existeix
   *     \post Es crea una instancia de Unzip
   */
  public Unzip(String path) {
    this.path = path;
  }

  /**
   * @brief Executa la transaccio
   *     \pre cert
   *     \post Es creen les estadístiques i el UnzipStream, al acabar s'atura el timer i es setejen
   *     els resultats
   */
  @Override
  public void execute() throws IOException {
    DataFactory factory = DataFactory.getInstance();
    ItemC item = ItemMapper.mapCompressedItem(factory.getFilesController().getFile(path));

    Statistics stats = new Statistics(item.getSize());

    factory.getStreamController().getUnzipStream(path).unzip();

    stats.stopTimer();
    setResult(stats);
  }
}
