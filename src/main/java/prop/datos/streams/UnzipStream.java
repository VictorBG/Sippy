package prop.datos.streams;


import prop.algorithms.Algorithm;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import prop.utils.Constants;
import prop.utils.FileUtils;

/**
 * @class UnzipStream
 * @brief Stream de sortida per descomprimir un item
 *
 *
 *     It recursively reads the headers and decodes the data using the method that the header
 *     indicates.
 *     It also preserves the state of the folders before zipping.
 *     Note: it does not override files, it will throw an exception if trying to decode a file to
 *     an
 *     existing file. For now it is not supported and not sure if support will be available for
 *     this
 *     type of functionality.
 *     Author: Victor Blanco.
 *     Unzips a sippy file format extension.
 *
 *     TODO: I think headerSize is not util and we could remove it from the header. The other fields
 *     are mandatory for the correct usage of this stream.
 */
public class UnzipStream {

  private String basePath;
  private String path;
  private DataInputStream dis;

  /**
   * @brief Constructora
   *
   *     \pre item no nul
   *     \post Nova instancia de UnzipStream
   */
  public UnzipStream(String path) throws IOException {
    if (!Constants.DEFAULT_ENCODING_EXTENSION.equals(FileUtils.getFileExtension(path))) {
      throw new UnsupportedEncodingException();
    }

    File f = new File(path);
    this.dis = new DataInputStream(new FileInputStream(f));
    this.path = path;
    basePath = path.replace(f.getName(), "");
  }

  public String getPath() {
    return path;
  }

  /**
   * @brief Inicia la operacio de descompressio de l'arxiu indicat a la constructora. Llegueix
   *     els headers i va, recursivament, descomprimmint regions de dades indicades als headers a un
   *     nou arxiu (tambe indicat al header)
   *
   *     \pre cert
   *     \post items descomprimit
   */
  public void unzip() throws IOException {
    try {
      int headerSize = getHeaderSize();
      Algorithm algorithm = getAlgorithm();
      int dataSize = getDataSize();
      String name = getName();

      File file = new File(basePath + name);
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }

      byte[] data = new byte[dataSize];
      dis.read(data, 0, dataSize);

      DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
      dos.writeBytes(new String(algorithm.getAlgorithm().decode(data)));
      dos.close();

      unzip();

    } catch (
        EOFException ignore) {
      // do not rethrow EOF exception, it is an exception we expect to get
    } finally {
      dis.close();
    }

  }

  /**
   * @brief Llegueix un enter del stream d'entrada
   *
   *     \pre cert
   *     \post enter
   */
  private int getHeaderSize() throws IOException {
    return dis.readInt();
  }

  /**
   * @brief Llegueix un byte del stream d'entrada
   *
   *     \pre cert
   *     \post algorisme identificat pel byte llegit
   */
  private Algorithm getAlgorithm() throws IOException {
    return Algorithm.valueOf(dis.readByte());
  }

  /**
   * @brief Llegueix un enter del stream d'entrada
   *
   *     \pre cert
   *     \post enter
   */
  private int getDataSize() throws IOException {
    return dis.readInt();
  }

  /**
   * @brief Llegueix un enter del stream d'entrada i despres llegueix tants bytes com aquest
   *     enter indica. Retorna un string que te per data aquests bytes
   *
   *     \pre cert
   *     \post string llegit del stream d'entrada
   */
  private String getName() throws IOException {
    int nameSize = dis.readInt();
    byte[] name = new byte[nameSize];
    dis.read(name, 0, nameSize);
    return new String(name);
  }
}
