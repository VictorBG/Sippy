package prop.dominio;

import java.io.IOException;

/**
 * @class Transaction
 * @brief Transaction model.
 * Author: Sergio Vazquez.
 */
public abstract class Transaction<Output> {

  private Output result;

  public abstract void execute() throws IOException;

  void setResult(Output result) {
    this.result = result;
  }

  public Output getResult() {
    return result;
  }

}
