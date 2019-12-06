package prop.dominio;

import prop.dominio.model.ItemC;
import prop.dominio.model.Statistics;
import prop.dominio.streams.UnzipStream;
import java.io.IOException;

/**
 * @class Unzip
 * @brief Unzip transaction.
 * Author: Sergio Vazquez.
 * Creates an {@link UnzipStream} with the provided {@link ItemC} and unzips it.
 * <p>
 * The {@link UnzipStream} is responsible of handle the decode of the data and creation of the files
 * (and folders if neccessary).
 */
public class Unzip extends Transaction<Statistics> {

  private ItemC item;

  /**
   * @brief Constructora
   *\pre item existeix
   * \post Es crea una instancia de Unzip
   */
  public Unzip(ItemC item) {
    this.item = item;
  }

  /**
   * @brief Executa la transaccio
   *\pre cert
   * \post Es creen les estadístiques i el UnzipStream, al acabar s'atura el timer i es setejen
   * els resultats
   */
  @Override
  public void execute() throws IOException {
    Statistics stats = new Statistics(item.getSize());
    new UnzipStream(item).unzip();
    stats.stopTimer();
    setResult(stats);
  }
}
