package prop.dominio.model;

import prop.dominio.model.uncompressed.FilePpm;
import prop.dominio.model.uncompressed.FileTxt;
import prop.dominio.model.uncompressed.Folder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import prop.utils.FileUtils;

/**
 * @class ItemNC
 * @brief Representation of a non compressed item. Used mainly for the zip operation.
 * Author: Sergio Vazquez
 */
public abstract class ItemNC {

  private File file;

  public ItemNC(String path) {
    this(new File(path));
  }

  public ItemNC(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  /**
   * Gets the size of the item. If it is a single file it returns the size of the file, if it is a
   * folder it returns the sum of the sizes of all files.
   *
   * @return the size of the item
   */
  public abstract double getSize();

  /**
   * Returns the list of the {@link ItemNC} inside the file.
   * <p>
   * If it is a single file it will return a list with only one element. If it is a folder it will
   * return all the items inside.
   * <p>
   * Pre: Items has been populated previously. Post: Return of the items
   *
   * @return items
   */
  public abstract List<prop.dominio.model.uncompressed.File> getItems();


  /**
   * Creates an {@link ItemNC} based on the extension of the {@link File} provided.
   * <p>
   * For now it only supports "ppm" and "txt" extension for a single file. It will throw an
   * exception if the file extension is not supported.
   *
   * @param file File to create the item
   * @return The created item
   * @throws UnsupportedEncodingException if the item extension is not supported by the program.
   */
  public static ItemNC create(File file) throws UnsupportedEncodingException {
    if (file.isDirectory()) {
      return new Folder(file);
    } else {
      String extension = FileUtils.getFileExtension(file.getPath());
      switch (extension) {
        case "ppm":
          return new FilePpm(file);
        case "txt":
          return new FileTxt(file);
        default:
          throw new UnsupportedEncodingException();
      }
    }
  }

}
