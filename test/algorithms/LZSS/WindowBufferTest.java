package algorithms.LZSS;

import static org.junit.Assert.*;

import algorithms.lzss.WindowBuffer;
import org.junit.Test;

public class WindowBufferTest {

    @Test
    public void testFillLookAheadBuffer() {
        algorithms.lzss.WindowBuffer w = new WindowBuffer(5,6);
        w.fillLookAheadBuffer("abracadabrarray");
        //w.getLookAheadString()

    }

    @Test
    public void testShiftBuffers() {
        algorithms.lzss.WindowBuffer w = new WindowBuffer(5,6);
        w.fillLookAheadBuffer("abracadabrarray");
        w.shiftLeft(5);


    }

}