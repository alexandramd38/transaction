package transaction.controller.itest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseITest {

  protected static final String ACCOUNT_ID = "849beade-e6cd-4f9d-8379-072f6e04b649";
  protected static final String BENEFICIARY_ACCOUNT_ID = "30171b6e-5959-4b72-9c85-559575f36fe2";
  protected static final String JWT_HEADER = "jwt";
  protected static final String CONTENT_TYPE = "application/v1+json";
  protected static final String ACCEPT = "application/v1+json";
  protected static final String IDEMPOTENCY_KEY = "idempotencyKey";
  protected static final String JWT =
      "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NzYwNjUyMSwiaWF0IjoxNjY3NjA2NTIxfQ.MVez01NYx_dPmB39qYVL64LIyQO_mR73lR0zK2nlTHw";
  protected static final String CUSTOMER_ID = "5ec7087b-06e3-41af-b80d-3acd618d9182";
  protected static final String ACCOUNT_BALANCE = "200000";
  protected static final String BLOCKED_AMOUNT = "0";
  protected static final String AMOUNT_TO_TRANSFER = "1000";
  protected static final String REMAINING_AMOUNT = "199000";
  protected static final String CURRENCY_CODE = "EUR";
  protected static final String ACCOUNT_STATUS = "ACTIVE";

  protected MockMvc mvc;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  public void getBalanceAccount(String amount) throws Exception {
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
}
