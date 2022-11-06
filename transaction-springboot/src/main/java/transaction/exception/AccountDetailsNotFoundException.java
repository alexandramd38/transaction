package transaction.exception;

/** Custom exception thrown when searched account details are not found / present in system. */
public class AccountDetailsNotFoundException extends RuntimeException {
  public AccountDetailsNotFoundException(String message) {
    super(message);
  }
}
