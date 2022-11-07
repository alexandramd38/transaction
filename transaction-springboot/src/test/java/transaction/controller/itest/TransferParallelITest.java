package transaction.controller.itest;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class TransferParallelITest extends BaseITest {

  private static final String FINAL_AMOUNT = "190000";

  @Test
  void initiateTransfersInParallel() throws Exception {
    getBalanceAccount(ACCOUNT_BALANCE);
    IntStream.range(0, 10)
        .parallel()
        .forEach(
            number -> {
              try {
                initiateTransfer();
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    getBalanceAccount(FINAL_AMOUNT);
  }
}
