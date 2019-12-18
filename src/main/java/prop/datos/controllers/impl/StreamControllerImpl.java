package prop.datos.controllers.impl;

import java.io.IOException;
import prop.datos.controllers.StreamController;
import prop.datos.streams.UnzipStream;
import prop.datos.streams.ZipStream;
import prop.datos.streams.impl.UnzipStreamImpl;
import prop.datos.streams.impl.ZipStreamImpl;

/**
 * Author: Víctor Blanco
 */
public class StreamControllerImpl implements StreamController {

  public ZipStream getZipStream(String path, String outputPath) throws IOException {
    return new ZipStreamImpl(path, outputPath);
  }

  public UnzipStream getUnzipStream(String path, String outputPath) throws IOException {
    return new UnzipStreamImpl(path, outputPath);
  }
}
