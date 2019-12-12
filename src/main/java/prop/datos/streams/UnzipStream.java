package prop.datos.streams;

import java.io.IOException;

/**
 * Author: Victor Blanco
 */
public interface UnzipStream extends Stream {

  /**
   * @brief Inicia la operacio de descompressio de l'arxiu indicat a la constructora. Llegueix
   *     els headers i va, recursivament, descomprimmint regions de dades indicades als headers a un
   *     nou arxiu (tambe indicat al header)
   *
   *     \pre cert
   *     \post items descomprimit
   */
  void unzip() throws IOException;
}
