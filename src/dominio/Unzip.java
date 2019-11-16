package dominio;

import dominio.model.ItemC;
import dominio.streams.UnzipStream;
import java.io.IOException;

/**
 * Author: Sergio Vazquez.
 * <p>
 * Unzip transaction.
 * <p>
 * Creates an {@link UnzipStream} with the provided {@link ItemC} and unzips it.
 * <p>
 * The {@link UnzipStream} is responsible of handle the decode of the data and creation of the files
 * (and folders if neccessary).
 */
public class Unzip extends Transaction<Void> {

  private ItemC item;

  public Unzip(ItemC item) {
    this.item = item;
  }

  @Override
  public void execute() throws IOException {
    new UnzipStream(item).unzip();
  }
}
