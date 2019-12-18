package prop.datos.controllers;

import java.io.IOException;
import prop.datos.streams.UnzipStream;
import prop.datos.streams.ZipStream;

/**
 * Author: Víctor Blanco
 *
 * Controlador pels objectes d'streams.
 */
public interface StreamController {

  ZipStream getZipStream(String path, String outputPath) throws IOException;

  UnzipStream getUnzipStream(String path, String outputPath) throws IOException;

}
