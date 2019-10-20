package domain;

public abstract class UseCase<Output, Params> {

  private Output result;
  private Params params;

  UseCase(Params params) {
    this.params = params;
  }

  public Params getParams() {
    return params;
  }

  public abstract void execute();

  public void setResult(Output result) {
    this.result = result;
  }

  public Output getResult() {
    return result;
  }

}
