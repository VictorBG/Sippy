package prop.dominio;

import java.io.IOException;

/**
 * Author: Sergio Vazquez.
 *
 * @class Transaction
 * @brief Transaction model.
 *
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
