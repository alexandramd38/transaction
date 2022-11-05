package transaction.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.transfer.api.model.AccountBalanceV1;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

  private static final String JWT = "JWT_TEST_VALUE";
  private static final String CORRELATION_ID = "CORRELATION_ID_TEST_VALUE";
  private static final UUID UUID_VALUE = UUID.randomUUID();

  @InjectMocks private AccountController accountController;

  @Test
  void getAccountBalance() {
    ResponseEntity<AccountBalanceV1> responseEntity =
        accountController.getAccountBalance(JWT, UUID_VALUE, CORRELATION_ID);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    AccountBalanceV1 accountBalance = responseEntity.getBody();
    assertNotNull(accountBalance);
    assertNull(accountBalance.getAccountId());
    assertNull(accountBalance.getName());
    assertNull(accountBalance.getCustomerId());
    assertNull(accountBalance.getAccountBalance());
    assertNull(accountBalance.getAvailableBalance());
    assertNull(accountBalance.getBlockedAccount());
    assertNull(accountBalance.getAccountStatus());
    assertNull(accountBalance.getTimestamp());
  }
}
