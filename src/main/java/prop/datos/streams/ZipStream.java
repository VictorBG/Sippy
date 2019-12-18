package prop.datos.streams;

import java.io.IOException;
import prop.algorithms.Algorithm;

/**
 * Author: Victor Blanco
 */
public interface ZipStream {

  /**
   * @brief Afegeix un item al stream per a que sigui compress i guardar a l'arxiu de sortida
   *
   *     \pre itemc no nul
   *     \post itemc compress i guardat a l'arxiu de sortida
   *
   *     Adds a file to the output stream. It writes the neccessary header and then writes to the
   *     output stream after applying the compress method.
   */
  void compressFile(String path, Algorithm alg) throws IOException;

  int getTotalSize();

}
