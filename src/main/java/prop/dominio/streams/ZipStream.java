package prop.dominio.streams;

import prop.algorithms.base.BaseAlgorithm;
import prop.dominio.model.ItemC;
import prop.dominio.model.ItemNC;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import prop.utils.FileUtils;

/**
 * @class ZipStream
 * @brief a new sippy file is created
 * Author: Victor Blanco
 *
 * {@link OutputStream} to output a "sippy" file format.
 *
 * A new sippy file is created in the root of the parent of the item passed by parameter on the
 * constructor, and then files can be added with no limit to that file.
 *
 * The "sippy" file format has the next internal layout structure:
 *
 * // TODO: Is HeaderSize this really neccessary? It is not used in the unzip operation.
 * *-------------*
 * |  HeaderSize | 4B
 * *-------------*
 * | Method used | 1B
 * *-------------*
 * |   DataSize  | 4B
 * *-------------*
 * |   NameSize  | 4B
 * *-------------*
 * |    Name     | ?B Undefined size by default, indicated in NameSize
 * *-------------*
 * |    DATA     | ?B Undefined size by default, indicated in DataSize
 * *-------------*
 *
 * HeaderSize: integer indicating the size of the header (all but DATA).
 *
 * MethodUsed: Byte indicating the compression algorithm used.
 *
 * DataSize: Integer indicating the size of the data. This limits the data to 2^32-1 bytes, which is
 * around 4GB.
 *
 * NameSize: Integer indicating the size of the name in the header.
 *
 * Name: Name of the archive, it is relative to the parent root of the compressed items. That means
 * that a single file will have its file name as a name but in a folder structure they will have
 * canonical structure until the root parent. ie: if zipping \\users\\ex\\test and it is a file in
 * the directory \\users\\ex\\test\\a\\b\\c.txt, the name will be a\\b\\c.txt.
 *
 * DATA: the encoded data of the file.
 */
public class ZipStream extends DataOutputStream {

  /**
   * @brief Metode static que instancia un nou ZipStream amb l'item a comprimir i un output stream que apunta a un file nou d'extensio "sippy" per guardar les dades
   *
   * \pre item no nul
   * \post Nova instancia de ZipStream
   *
   */
  public static ZipStream create(ItemNC itemNC) throws IOException {
    File f = new File(FileUtils
        .changeExtension(itemNC.getFile().getPath(), FileUtils.DEFAULT_ENCODING_EXTENSION));
    if (!f.exists()) {
      f.getParentFile().mkdirs();
      f.createNewFile();
    }
    return new ZipStream(new FileOutputStream(f), itemNC.getFile());
  }

  private int totalSize;
  private String basePath;

  /**
   *
   * @brief Constructora
   *
   * \pre OutputStream no nul, file no nul
   * \post Nova instancia de ZipStream
   *
   * Creates a new data output stream to write data to the specified underlying output stream. The
   * counter <code>written</code> is set to zero.
   *
   * @param out the underlying output stream, to be saved for later use.
   *
   * @see FilterOutputStream#out
   */
  private ZipStream(OutputStream out, File file) {
    super(out);
    totalSize = 0;
    basePath = file.getAbsolutePath().replace(file.getName(), "");
  }

  /**
   *
   * @brief Afegeix un itemc al stream per a que sigui compress i guardar a l'arxiu de sortida
   *
   * \pre itemc no nul
   * \post itemc compress i guardat a l'arxiu de sortida
   *
   * Adds a file to the output stream. It writes the neccessary header and then writes to the output
   * stream after applying the compress method.
   *
   * @param itemC item to add
   */
  public void addFile(ItemC itemC) throws IOException {
    byte method = itemC.getMethod().getId();

    File file = itemC.getFile();
    String name = file.getAbsolutePath().replace(basePath, "");
    int nameSize = name.length();

    BaseAlgorithm algorithm = itemC.getMethod().getAlgorithm();
    byte[] data = algorithm.encode(algorithm.readFile(itemC.getFile()));

    int dataSize = data.length;

    int headerSize = 13 + nameSize;

    writeInt(headerSize);
    writeByte(method);
    writeInt(dataSize);
    writeInt(nameSize);
    writeBytes(name);

    write(data, 0, data.length);

    totalSize += headerSize + dataSize;
  }

  public int getTotalSize() {
    return totalSize;
  }
}
