package prop.dominio.mappers;

import static prop.utils.Constants.ENCODING_EXTENSION_PPM;
import static prop.utils.Constants.ENCODING_EXTENSION_TXT;

import java.util.stream.Collectors;
import prop.datos.model.FolderBO;
import prop.datos.model.ItemBO;
import prop.dominio.model.ItemC;
import prop.dominio.model.ItemNC;
import prop.dominio.model.uncompressed.FilePpm;
import prop.dominio.model.uncompressed.FileTxt;
import prop.dominio.model.uncompressed.Folder;

/**
 * Author: Víctor Blanco
 */
public final class ItemMapper {

  public static ItemNC mapItem(ItemBO itemBO) {
    ItemNC result;
    if (itemBO instanceof FolderBO) {
      FolderBO folderBO = (FolderBO) itemBO;
      Folder folder = new Folder(itemBO.getPath());
      folder.setItems(folderBO
          .getItems()
          .stream()
          .map(ItemMapper::mapItem)
          .collect(Collectors.toList()));
      result = folder;
    } else {
      switch (itemBO.getExtension()) {
        case ENCODING_EXTENSION_PPM:
          result = new FilePpm(itemBO.getPath());
          break;
        default:
        case ENCODING_EXTENSION_TXT:
          result = new FileTxt(itemBO.getPath());
      }
    }

    result.setSize(itemBO.getSize());
    return result;
  }

  public static ItemC mapCompressedItem(ItemBO itemBO) {
    ItemC result = new ItemC(itemBO.getPath());
    result.setSize(itemBO.getSize());
    return result;
  }

}
