package domain;

public abstract class Transaction<Output> {

  private Output result;

  public abstract void execute();

  public void setResult(Output result) {
    this.result = result;
  }

  public Output getResult() {
    return result;
  }

}
