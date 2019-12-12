package prop.datos.controllers;

import java.io.IOException;
import prop.datos.streams.UnzipStream;
import prop.datos.streams.ZipStream;

/**
 * Author: Víctor Blanco
 *
 * Controller for the streams object.
 */
public interface StreamController {

  ZipStream getZipStream(String path) throws IOException;

  UnzipStream getUnzipStream(String path) throws IOException;

}
