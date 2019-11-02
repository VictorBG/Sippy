package algorithms.lzss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import algorithms.base.BaseAlgorithm;

public class LZSS implements BaseAlgorithm<String, String> {

  @Override
  public Optional<String> encode(String file) {
    WindowBuffer w = new WindowBuffer(9,9,"abracadabrarray");
    w.fillLookAheadBuffer();
    String result = "";

    while (!w.lookAheadIsEmpty()) {
      EncodedString es = w.findMatch();
      if (es.getLength() > 0) {
        es.print();
        w.shiftLeft(es.getLength()+1);
      } else {
        System.out.printf("00%c", w.getFirstCharLookAheadBuffer());
        w.shiftLeft(1);
      }

    }

    //no se que hay que hacer aqui exactamente..
    return Optional.of("HOLA");
  }


  private static String append(String current) {
    String[] name = current.split("");
    return name[name.length - 1];
  }
}
