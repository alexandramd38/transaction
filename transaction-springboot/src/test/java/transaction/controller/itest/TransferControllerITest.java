package transaction.controller.itest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class TransferControllerITest extends BaseITest {

  @Test
  @Transactional
  void transfer() throws Exception {
    getBalanceAccount(ACCOUNT_BALANCE);
    initiateTransfer();
    getBalanceAccount(REMAINING_AMOUNT);
  }
}
