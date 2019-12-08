package prop.datos;

import java.io.IOException;
import prop.datos.streams.UnzipStream;
import prop.datos.streams.ZipStream;

/**
 * Author: Víctor Blanco
 */
public class StreamController {

  public ZipStream getZipStream(String path) throws IOException {
    return ZipStream.create(path);
  }

  public UnzipStream getUnzipStream(String path) throws IOException {
    return new UnzipStream(path);
  }
}
