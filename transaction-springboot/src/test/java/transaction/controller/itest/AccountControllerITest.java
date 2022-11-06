package transaction.controller.itest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class AccountControllerITest extends BaseITest {

  @Test
  void getAccountBalance() throws Exception {
    getBalanceAccount(ACCOUNT_BALANCE);
  }
}
