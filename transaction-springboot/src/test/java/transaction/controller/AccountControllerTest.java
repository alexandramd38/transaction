package transaction.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.transfer.api.model.AccountBalanceV1;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import transaction.service.AccountService;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

  private static final String JWT = "JWT_TEST_VALUE";
  private static final String CORRELATION_ID = "CORRELATION_ID_TEST_VALUE";
  private static final String ACCOUNT_ID = UUID.randomUUID().toString();

  @Mock private AccountService accountService;
  @Mock private AccountBalanceV1 accountBalanceV1;

  @InjectMocks private AccountController accountController;

  @Test
  void getAccountBalance() {

    when(accountService.getAccountBalance(ACCOUNT_ID)).thenReturn(accountBalanceV1);

    ResponseEntity<AccountBalanceV1> responseEntity =
        accountController.getAccountBalance(JWT, ACCOUNT_ID, CORRELATION_ID);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(accountBalanceV1, responseEntity.getBody());
  }
}
