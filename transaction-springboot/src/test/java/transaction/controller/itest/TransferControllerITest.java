package transaction.controller.itest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.api.model.InitiateTransferBodyV1;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.TransactionServiceApplication;

@SpringBootTest(classes = TransactionServiceApplication.class)
public class TransferControllerITest extends BaseITest {

  private static final String REFERENCE = "0012567848";
  private static final String DATE_TIME = "2022-12-06 13:12:11";
  private static final String DESCRIPTION = "Money for lunch";

  static final ObjectMapper mapper = new ObjectMapper();

  @Test
  void transfer() throws Exception {

    getBalanceAccount(ACCOUNT_BALANCE);

    mvc.perform(
            post("/transfer")
                .header(JWT_HEADER, JWT)
                .header(IDEMPOTENCY_KEY, UUID.randomUUID())
                .contentType(CONTENT_TYPE)
                .accept(ACCEPT)
                .content(mapper.writeValueAsString(getTransferBody())))
        .andExpect(status().isCreated());
    getBalanceAccount(REMAINING_AMOUNT);
  }

  private InitiateTransferBodyV1 getTransferBody() {
    return new InitiateTransferBodyV1()
        .reference(REFERENCE)
        .amount(AMOUNT_TO_TRANSFER)
        .currencyCode(CURRENCY_CODE)
        .sourceAccount(UUID.fromString(ACCOUNT_ID))
        .beneficiaryAccount(UUID.fromString(BENEFICIARY_ACCOUNT_ID))
        .description(DESCRIPTION)
        .dateTime(DATE_TIME);
  }
}
