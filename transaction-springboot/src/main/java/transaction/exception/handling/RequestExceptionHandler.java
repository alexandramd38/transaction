package transaction.exception.handling;

import static java.time.ZoneOffset.UTC;

import com.transfer.api.model.Fault;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RequestExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(RequestExceptionHandler.class);

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Fault> handleInvalidFieldsException(
      MethodArgumentNotValidException exception) {
    var fault = new Fault();
    List<String> errors = new ArrayList<>();
    if (exception.getBindingResult().hasErrors()) {
      errors =
          exception.getBindingResult().getFieldErrors().stream()
              .map(e -> e.getField() + " " + e.getDefaultMessage())
              .collect(Collectors.toList());
    }
    fault.setMessage(errors.toString());
    fault.setCode("4000");
    fault.setDateTime(OffsetDateTime.now(UTC));
    logger.warn("Request processing failed due to validation errors: " + fault);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fault);
  }
}
