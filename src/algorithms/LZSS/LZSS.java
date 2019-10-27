package algorithms.lzss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import algorithms.base.BaseAlgorithm;

public class LZSS implements BaseAlgorithm<String, String> {

  @Override
  public Optional<String> encode(String file) {
    //String[] input = file.split("");
    String input = "abracadabrarray";
    algorithms.LZSS.WindowBuffer windowBuffer = new algorithms.LZSS.WindowBuffer(8,6, "abracadabrarray");

    while (! windowBuffer.lookAheadIsEmpty()) {

    }


    return Optional.of("HOLA");
  }


  private static String append(String current) {
    String[] name = current.split("");
    return name[name.length - 1];
  }
}
