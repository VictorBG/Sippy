package prop.datos.streams.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;
import prop.datos.streams.ZipStream;
import prop.utils.Constants;
import prop.utils.FileUtils;

/**
 * Author: Victor Blanco
 *
 * @class ZipStream
 * @brief a new sippy file is created
 *
 *     {@link OutputStream} to output a "sippy" file format.
 *
 *     A new sippy file is created in the outputPath, and then files can be added with no limit
 *     to that file.
 *
 *     The "sippy" file format has the next internal layout structure:
 *
 *     -------------
 *     | Method used | 1B
 *     -------------
 *     |   DataSize  | 4B
 *     -------------
 *     |   NameSize  | 4B
 *     -------------
 *     |    Name     | ?B Undefined size by default, indicated in NameSize
 *     -------------
 *     |    DATA     | ?B Undefined size by default, indicated in DataSize
 *     -------------
 *
 *     MethodUsed: Byte indicating the compression algorithm used.
 *
 *     DataSize: Integer indicating the size of the data. This limits the data to 2^32-1 bytes,
 *     which is around 4GB.
 *
 *     NameSize: Integer indicating the size of the name in the header.
 *
 *     Name: Name of the archive, it is relative to the parent root of the compressed items. That
 *     means that a single file will have its file name as a name but in a folder structure they
 *     will have canonical structure until the root parent. ie: if zipping \\users\\ex\\test and
 *     it is a file in the directory \\users\\ex\\test\\a\\b\\c.txt, the name will be a\\b\\c.txt.
 *
 *     DATA: the encoded data of the file.
 */
public class ZipStreamImpl implements ZipStream {

  private int totalSize;
  private String basePath;
  private DataOutputStream dos;

  /**
   * @brief Constructora
   *
   * \pre cert
   * \post Crea una instancia de ZipStreamImpl i crea un arxiu d'extensió sippy on
   * escriure els arxius comprimits
   *
   * @param inputFilePath  Path d'entrada
   * @param outputPath     Path de sortida
   * @throws IOException
   */
  public ZipStreamImpl(String inputFilePath, String outputPath) throws IOException {
    File outputFile = createSippyFile(outputPath);

    File inputFile = new File(inputFilePath);
    this.basePath = inputFile.getAbsolutePath().replace(inputFile.getName(), "");
    this.totalSize = 0;
    this.dos = new DataOutputStream(new FileOutputStream(outputFile));

    this.dos.writeByte(inputFile.isDirectory() ? 0b0 : 0b1);
  }

  /**
   * @brief Crea l'arxiu identificat pel path i si no és de tipus sippy, el renombra.
   *
   * @param path    Path de l'arxiu a crear si no existeix
   * @return        File de l'arxiu identificat per path
   * @throws IOException
   */
  private File createSippyFile(String path) throws IOException {
    if (!FileUtils.getFileExtension(path).equals(Constants.DEFAULT_ENCODING_EXTENSION)) {
      path = FileUtils.changeExtension(path, Constants.DEFAULT_ENCODING_EXTENSION);
    }
    File f = new File(path);
    if (!f.exists()) {
      f.getParentFile().mkdirs();
      f.createNewFile();
    }
    return f;
  }

  @Override
  public void compressFile(String path, Algorithm alg) throws IOException {

    File file = new File(path);
    String name = file.getAbsolutePath().replace(basePath, "");
    int nameSize = name.length();

    BaseAlgorithm algorithm = alg.getAlgorithm();
    byte[] data = algorithm.encode(algorithm.readFile(file));

    byte algorithmID = algorithm.getAlgorithmUsed().getId();
    int dataSize = data.length;

    int headerSize = 9 + nameSize;

    dos.writeByte(algorithmID);
    dos.writeInt(dataSize);
    dos.writeInt(nameSize);
    dos.writeBytes(name);

    dos.write(data, 0, data.length);

    totalSize += headerSize + dataSize;
  }

  public int getTotalSize() {
    return totalSize;
  }
}
