package transaction.controller.itest;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class AccountControllerITest extends BaseITest {

  private static final String ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String BALANCE = "1000";

  @Test
  @Transactional
  void getAccountBalance() throws Exception {
    saveAccountEntity(ACCOUNT_ID, BALANCE);
    getBalanceAccount(ACCOUNT_ID, BALANCE);
  }
}
