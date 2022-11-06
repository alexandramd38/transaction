package transaction.exception;

/**
 * Custom exception thrown when there are not enough money in the account from which the transfer
 * should be made from.
 */
public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(String message) {
    super(message);
  }
}
