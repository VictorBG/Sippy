package prop.datos.streams.impl;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import prop.algorithms.Algorithm;
import prop.datos.exception.UnsupportedOutputDirectoryPathname;
import prop.datos.streams.UnzipStream;
import prop.utils.Constants;
import prop.utils.FileUtils;

/**
 * Author: Victor Blanco.
 *
 * @class UnzipStream
 * @brief Stream de sortida per descomprimir un item
 *
 *     It recursively reads the headers and decodes the data using the method that the header
 *     indicates.
 *
 *     It also preserves the state of the folders before zipping.
 *
 *     Unzips a sippy file format extension.
 */
public class UnzipStreamImpl implements UnzipStream {

  private String basePath;
  private DataInputStream dis;
  private boolean isDirectory;

  /**
   * @brief Constructora
   *
   * \pre item no nul
   * \post Nova instancia de UnzipStreamImpl
   */
  public UnzipStreamImpl(String inputPath, String outputDirectoryPath)
      throws IOException {

    checkCorrectFormat(inputPath);
    checkOutputDirectory(outputDirectoryPath);

    File f = new File(inputPath);
    this.dis = new DataInputStream(new FileInputStream(f));
    this.basePath = outputDirectoryPath;
    normalizeBasePath();

    this.isDirectory = this.dis.readByte() == 0b0;
  }

  /**
   * @brief Comprova que el path sigui un arxiu d'extensió sippy
   *
   * \pre cert
   * \post Llança una excepció si el path no es un arxiu sippy
   */
  private void checkCorrectFormat(String path) throws UnsupportedEncodingException {
    if (!Constants.DEFAULT_ENCODING_EXTENSION.equals(FileUtils.getFileExtension(path))) {
      throw new UnsupportedEncodingException();
    }
  }

  /**
   * @brief Comprova que el path sigui un directori
   *
   * \pre cert
   * \post Llança una excepció si el path no es un directori
   */
  private void checkOutputDirectory(String path) throws UnsupportedOutputDirectoryPathname {
    File outputDirectory = new File(path);

    if (!outputDirectory.isDirectory()) {
      throw new UnsupportedOutputDirectoryPathname();
    }

  }

  /**
   * @brief Afegeix un separador al final del {@link #basePath}
   *     si no hi és.
   *
   * \pre cert
   * \post basePath amb el separador del sistema
   */
  private void normalizeBasePath() {
    if (!basePath.endsWith(File.separator)) {
      basePath += File.separator;
    }
  }

  @Override
  public void unzip() throws IOException {
    try {
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

      if (isDirectory) {
        unzip();
      } else {
        FileUtils.openFile(file);
      }
    } catch (EOFException ignore) {
      if (isDirectory) {
        FileUtils.openFile(new File(basePath));
      }
      // do not rethrow EOF exception, it is an exception we expect to get
    } finally {
      dis.close();
    }

  }

  /**
   * @brief Llegueix un byte del stream d'entrada
   *
   * \pre cert
   * \post algorisme identificat pel byte llegit
   */
  private Algorithm getAlgorithm() throws IOException {
    return Algorithm.valueOf(dis.readByte());
  }

  /**
   * @brief Llegueix un enter del stream d'entrada
   *
   * \pre cert
   *  \post enter
   */
  private int getDataSize() throws IOException {
    return dis.readInt();
  }

  /**
   * @brief Llegueix un enter del stream d'entrada i despres llegueix tants bytes com aquest
   *     enter indica. Retorna un string que te per data aquests bytes
   *
   * \pre cert
   *  \post string llegit del stream d'entrada
   */
  private String getName() throws IOException {
    int nameSize = dis.readInt();
    byte[] name = new byte[nameSize];
    dis.read(name, 0, nameSize);
    return new String(name);
  }
}
