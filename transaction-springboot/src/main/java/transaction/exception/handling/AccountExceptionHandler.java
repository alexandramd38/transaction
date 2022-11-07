package transaction.exception.handling;

import static java.time.ZoneOffset.UTC;

import com.transfer.api.model.Fault;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import transaction.exception.AccountDetailsNotFoundException;
import transaction.exception.InsufficientFundsException;

@ControllerAdvice
public class AccountExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(AccountExceptionHandler.class);

  @ExceptionHandler(AccountDetailsNotFoundException.class)
  public ResponseEntity<Fault> handleAccountDetailsNotFoundException(
      AccountDetailsNotFoundException exception) {
    logger.warn("Account details are not found. " + exception.getMessage());
    var fault = new Fault();
    fault.setMessage("Invalid accountId");
    fault.setCode("4001");
    fault.setDateTime(OffsetDateTime.now(UTC));
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fault);
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<Fault> handleInsufficientFundsException(
      InsufficientFundsException exception) {
    logger.warn(exception.getMessage());
    var fault = new Fault();
    fault.setMessage("Insufficient funds");
    fault.setCode("4002");
    fault.setDateTime(OffsetDateTime.now(UTC));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fault);
  }
}
