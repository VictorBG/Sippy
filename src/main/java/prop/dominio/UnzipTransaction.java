package prop.dominio;

import prop.datos.DataFactory;
import prop.dominio.mappers.ItemMapper;
import prop.dominio.model.ItemC;
import prop.dominio.model.Statistics;
import java.io.IOException;

/**
 * Author: Sergio Vazquez.
 *
 * @class UnzipTransaction
 * @brief Unzip transaction.
 */
public class UnzipTransaction extends Transaction<Statistics> {

  private String path;
  private String outputPath;

  /**
   * @brief Constructora
   * \pre item existeix
   *  \post Es crea una instancia de Unzip
   */
  public UnzipTransaction(String path, String outputPath) {
    this.path = path;
    this.outputPath = outputPath;
  }

  /**
   * @brief Executa la transaccio
   * \pre cert
   *  \post Es creen les estadístiques i el UnzipStream, al acabar s'atura el timer i es setejen
   *     els resultats
   */
  @Override
  public void execute() throws IOException {
    DataFactory factory = DataFactory.getInstance();
    ItemC item = ItemMapper.mapCompressedItem(factory.getFilesController().getFile(path));

    Statistics stats = new Statistics(item.getSize());

    factory.getStreamController().getUnzipStream(path, outputPath).unzip();

    stats.stopTimer();
    setResult(stats);
  }
}
