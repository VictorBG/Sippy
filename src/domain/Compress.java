package domain;

import algorithms.Algorithms;
import domain.Compress.Params;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import utils.FileIO;

public class Compress extends UseCase<Void, Params> {

  public Compress(Params params) {
    super(params);
  }

  @Override
  public void execute() {
    Params params = getParams();
    try {
      //Load (should be on data layer)
      byte[] file = FileIO.readFile(new File(params.path));
      Optional<byte[]> compressed = params.algorithm.getAlgorithm().encode(file);
      //Save (should be on data layer)
      compressed.ifPresent(c -> FileIO.save(c, FileIO.changeExtension(params.path, "sippy")));
    } catch (IOException ignore) {
    }
  }

  public static final class Params {

    private final String path;
    private final Algorithms algorithm;

    public Params(String path, Algorithms algorithm) {
      this.path = path;
      this.algorithm = algorithm;
    }

    public String getItem() {
      return path;
    }

    public Algorithms getAlgorithm() {
      return algorithm;
    }
  }
}
