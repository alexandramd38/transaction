package transaction.controller.itest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.api.model.InitiateTransferBodyV1;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import transaction.model.TransferStatus;

public class BaseITest {

  protected static final String ACCOUNT_BALANCE = "200000";
  protected static final String REMAINING_AMOUNT = "199000";

  private static final String ACCOUNT_ID = "849beade-e6cd-4f9d-8379-072f6e04b649";
  private static final String BENEFICIARY_ACCOUNT_ID = "30171b6e-5959-4b72-9c85-559575f36fe2";
  private static final String JWT_HEADER = "jwt";
  private static final String CONTENT_TYPE = "application/v1+json";
  private static final String ACCEPT = "application/v1+json";
  private static final String IDEMPOTENCY_KEY = "idempotencyKey";
  private static final String JWT =
      "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NzYwNjUyMSwiaWF0IjoxNjY3NjA2NTIxfQ.MVez01NYx_dPmB39qYVL64LIyQO_mR73lR0zK2nlTHw";
  private static final String CUSTOMER_ID = "5ec7087b-06e3-41af-b80d-3acd618d9182";
  private static final String BLOCKED_AMOUNT = "0";
  private static final String AMOUNT_TO_TRANSFER = "1000";
  private static final String CURRENCY_CODE = "EUR";
  private static final String ACCOUNT_STATUS = "ACTIVE";
  private static final String REFERENCE = "0012567848";
  private static final String DATE_TIME = "2022-12-06 13:12:11";
  private static final String DESCRIPTION = "Money for lunch";

  private MockMvc mvc;
  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected void getBalanceAccount(String amount) throws Exception {
    mvc.perform(
            get("/account/{accountId}/balance", ACCOUNT_ID)
                .header(JWT_HEADER, JWT)
                .contentType(CONTENT_TYPE)
                .accept(ACCEPT))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(ACCEPT))
        .andExpect(jsonPath("accountId").value(ACCOUNT_ID))
        .andExpect(jsonPath("name").isEmpty())
        .andExpect(jsonPath("customerId").value(CUSTOMER_ID))
        .andExpect(jsonPath("accountBalance").value(amount))
        .andExpect(jsonPath("availableBalance").value(amount))
        .andExpect(jsonPath("blockedAmount").value(BLOCKED_AMOUNT))
        .andExpect(jsonPath("currencyCode").value(CURRENCY_CODE))
        .andExpect(jsonPath("accountStatus").value(ACCOUNT_STATUS))
        .andExpect(jsonPath("timestamp").isNotEmpty());
  }

  protected void initiateTransfer() throws Exception {
    mvc.perform(
            post("/transfer")
                .header(JWT_HEADER, JWT)
                .header(IDEMPOTENCY_KEY, UUID.randomUUID())
                .contentType(CONTENT_TYPE)
                .accept(ACCEPT)
                .content(mapper.writeValueAsString(getTransferBody())))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("transferId").isNotEmpty())
        .andExpect(jsonPath("reference").value(REFERENCE))
        .andExpect(jsonPath("amount").value(AMOUNT_TO_TRANSFER))
        .andExpect(jsonPath("currencyCode").value(CURRENCY_CODE))
        .andExpect(jsonPath("sourceAccount").value(ACCOUNT_ID))
        .andExpect(jsonPath("beneficiaryAccount").value(BENEFICIARY_ACCOUNT_ID))
        .andExpect(jsonPath("creationDateTime").isNotEmpty())
        .andExpect(jsonPath("status").value(TransferStatus.TRANSFERRED.name()));
  }

  protected InitiateTransferBodyV1 getTransferBody() {
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
