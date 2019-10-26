package algorithms.lzss;

/**
 * Class WindowBuffer
 */
public class WindowBuffer {

    char[] buffers;

    int searchL;
    int searchR;
    int lookAheadL;
    int lookAheadR;

    public int getSearchL() {
        return searchL;
    }

    public int getSearchR() {
        return searchR;
    }

    public int getLookAheadL() {
        return lookAheadL;
    }

    public int getLookAheadR() {
        return lookAheadR;
    }

    public WindowBuffer (int searchBufferSize, int lookAheadBufferSize) {
        buffers = new char[searchBufferSize+lookAheadBufferSize];
        searchL = 0;
        searchR = searchBufferSize-1;
        lookAheadL = searchBufferSize;
        lookAheadR = searchBufferSize+lookAheadBufferSize-1;
    }

}
