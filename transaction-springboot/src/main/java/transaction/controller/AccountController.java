package transaction.controller;

import com.transfer.api.controller.AccountApi;
import com.transfer.api.model.AccountBalanceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import transaction.service.AccountService;

@RestController
public class AccountController implements AccountApi {

  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  public ResponseEntity<AccountBalanceV1> getAccountBalance(
      String jwt, String accountId, String correlationId) {
    logger.info("Received request for account id = " + accountId);
    return ResponseEntity.ok(accountService.getAccountBalance(accountId));
  }
}
