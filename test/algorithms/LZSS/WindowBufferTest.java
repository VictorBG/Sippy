package algorithms.LZSS;

import static org.junit.Assert.*;

import algorithms.LZSS.WindowBuffer;
import org.junit.Test;

public class WindowBufferTest {

    @Test
    public void testFillLookAheadBuffer() {
        algorithms.LZSS.WindowBuffer w = new WindowBuffer(5,6, "abracadabrarray");
        w.fillLookAheadBuffer();
        assertEquals("abraca" ,w.toString());

    }
    public void testContinue_matching() {
        algorithms.LZSS.WindowBuffer w = new WindowBuffer(5,6, "abracadabrarray");
        w.fillLookAheadBuffer();
        //w.continueMatching(...)
    }

    @Test
    public void testShiftBuffersLeft() {
        algorithms.LZSS.WindowBuffer w = new WindowBuffer(5,6, "holacomoestas");
        w.fillLookAheadBuffer();
        w.shiftLeft(5);
        assertEquals("holacomoest", w.toString());

    }

    @Test
    public void testFindMatch1() {
        algorithms.LZSS.WindowBuffer w = new WindowBuffer(5,6, "abracadabrarray");
        w.fillLookAheadBuffer();
        w.shiftLeft(3);
        EncodedString es = w.findMatch();
        assertEquals('a' ,es.c);
        assertEquals(0, es.length);
        assertEquals(3, es.offset);
    }



}