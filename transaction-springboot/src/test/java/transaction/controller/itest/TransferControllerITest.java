package transaction.controller.itest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class TransferControllerITest extends BaseITest {

  private static final String SOURCE_ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String BENEFICIARY_ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String SOURCE_BALANCE = "1000";
  private static final String BENEFICIARY_BALANCE = "0";
  private static final String AMOUNT_TO_TRANSFER = "100";
  private static final String REMAINING_SOURCE_AMOUNT = "900";
  private static final String REMAINING_BENEFICIARY_AMOUNT = "100";

  @Test
  @Transactional
  void transfer() throws Exception {
    saveAccountEntity(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    saveAccountEntity(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    getBalanceAccount(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    initiateTransfer(
        SOURCE_ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER, status().isCreated());

    getBalanceAccount(SOURCE_ACCOUNT_ID, REMAINING_SOURCE_AMOUNT);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, REMAINING_BENEFICIARY_AMOUNT);
  }
}
