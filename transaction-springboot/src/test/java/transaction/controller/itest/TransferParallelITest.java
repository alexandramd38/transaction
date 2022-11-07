package transaction.controller.itest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.TransactionServiceApplication;
import transaction.repository.AccountRepository;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class TransferParallelITest extends BaseITest {

  private static final String SOURCE_ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String BENEFICIARY_ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String SOURCE_BALANCE = "1000";
  private static final String BENEFICIARY_BALANCE = "0";
  private static final String AMOUNT_TO_TRANSFER = "100";
  private static final String REMAINING_SOURCE_AMOUNT = "0";
  private static final String REMAINING_BENEFICIARY_AMOUNT = "1000";

  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    var sourceAccount = accountRepository.getAccountEntityByAccountId(SOURCE_ACCOUNT_ID);
    if (sourceAccount.isPresent()) {
      accountRepository.delete(sourceAccount.get());
    }
    var beneficiaryAccount = accountRepository.getAccountEntityByAccountId(BENEFICIARY_ACCOUNT_ID);
    if (beneficiaryAccount.isPresent()) {
      accountRepository.delete(beneficiaryAccount.get());
    }
  }

  @Test
  void initiateTransfersInParallel() throws Exception {
    saveAccountEntity(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    saveAccountEntity(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    getBalanceAccount(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    IntStream.range(0, 10)
        .parallel()
        .forEach(
            number -> {
              try {
                initiateTransfer(
                    SOURCE_ACCOUNT_ID,
                    BENEFICIARY_ACCOUNT_ID,
                    AMOUNT_TO_TRANSFER,
                    status().isCreated());
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    getBalanceAccount(SOURCE_ACCOUNT_ID, REMAINING_SOURCE_AMOUNT);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, REMAINING_BENEFICIARY_AMOUNT);
  }

  @Test
  void initiateTransfersInsufficientFunds() throws Exception {
    saveAccountEntity(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    saveAccountEntity(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    getBalanceAccount(SOURCE_ACCOUNT_ID, SOURCE_BALANCE);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, BENEFICIARY_BALANCE);

    IntStream.range(0, 11)
        .parallel()
        .forEach(
            number -> {
              try {
                initiateTransfer(SOURCE_ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    getBalanceAccount(SOURCE_ACCOUNT_ID, REMAINING_SOURCE_AMOUNT);
    getBalanceAccount(BENEFICIARY_ACCOUNT_ID, REMAINING_BENEFICIARY_AMOUNT);
  }
}
