package transaction.controller.itest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.api.model.InitiateTransferBodyV1;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import transaction.model.TransferStatus;
import transaction.repository.AccountRepository;
import transaction.repository.entity.AccountEntity;

public class BaseITest {

  private static final String JWT_HEADER = "jwt";
  private static final String CONTENT_TYPE = "application/v1+json";
  private static final String ACCEPT = "application/v1+json";
  private static final String IDEMPOTENCY_KEY = "idempotencyKey";
  private static final String JWT =
      "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NzYwNjUyMSwiaWF0IjoxNjY3NjA2NTIxfQ.MVez01NYx_dPmB39qYVL64LIyQO_mR73lR0zK2nlTHw";
  private static final String CUSTOMER_ID = "5ec7087b-06e3-41af-b80d-3acd618d9182";
  private static final String BLOCKED_AMOUNT = "0";
  private static final String CURRENCY_CODE = "EUR";
  private static final String ACCOUNT_STATUS = "ACTIVE";
  private static final String REFERENCE = "0012567848";
  private static final String DATE_TIME = "2022-12-06 13:12:11";
  private static final String DESCRIPTION = "Money for lunch";
  private static final String ACCOUNT_NAME = "Currency account";

  private MockMvc mvc;
  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected void getBalanceAccount(String sourceAccountId, String sourceBalance) throws Exception {
    mvc.perform(
            get("/account/{accountId}/balance", sourceAccountId)
                .header(JWT_HEADER, JWT)
                .contentType(CONTENT_TYPE)
                .accept(ACCEPT))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(ACCEPT))
        .andExpect(jsonPath("accountId").value(sourceAccountId))
        .andExpect(jsonPath("name").value(ACCOUNT_NAME))
        .andExpect(jsonPath("customerId").value(CUSTOMER_ID))
        .andExpect(jsonPath("accountBalance").value(sourceBalance))
        .andExpect(jsonPath("availableBalance").value(sourceBalance))
        .andExpect(jsonPath("blockedAmount").value(BLOCKED_AMOUNT))
        .andExpect(jsonPath("currencyCode").value(CURRENCY_CODE))
        .andExpect(jsonPath("accountStatus").value(ACCOUNT_STATUS))
        .andExpect(jsonPath("timestamp").isNotEmpty());
  }

  protected void initiateTransfer(
      String sourceAccountId, String beneficiaryAccountId, String amount, ResultMatcher status)
      throws Exception {
    mvc.perform(
            post("/transfer")
                .header(JWT_HEADER, JWT)
                .header(IDEMPOTENCY_KEY, UUID.randomUUID())
                .contentType(CONTENT_TYPE)
                .accept(ACCEPT)
                .content(
                    mapper.writeValueAsString(
                        getTransferBody(sourceAccountId, beneficiaryAccountId, amount))))
        .andExpect(status)
        .andExpect(jsonPath("transferId").isNotEmpty())
        .andExpect(jsonPath("reference").value(REFERENCE))
        .andExpect(jsonPath("amount").value(amount))
        .andExpect(jsonPath("currencyCode").value(CURRENCY_CODE))
        .andExpect(jsonPath("sourceAccount").value(sourceAccountId))
        .andExpect(jsonPath("beneficiaryAccount").value(beneficiaryAccountId))
        .andExpect(jsonPath("creationDateTime").isNotEmpty())
        .andExpect(jsonPath("status").value(TransferStatus.TRANSFERRED.name()));
  }

  protected void initiateTransfer(
      String sourceAccountId, String beneficiaryAccountId, String amount) throws Exception {
    mvc.perform(
        post("/transfer")
            .header(JWT_HEADER, JWT)
            .header(IDEMPOTENCY_KEY, UUID.randomUUID())
            .contentType(CONTENT_TYPE)
            .accept(ACCEPT)
            .content(
                mapper.writeValueAsString(
                    getTransferBody(sourceAccountId, beneficiaryAccountId, amount))));
  }

  protected AccountEntity saveAccountEntity(String accountId, String balance) {
    var accountEntity = new AccountEntity();
    accountEntity.setAccountId(accountId);
    accountEntity.setCustomerId(UUID.fromString(CUSTOMER_ID));
    accountEntity.setBalance(new BigInteger(balance));
    accountEntity.setBlockedAmount(BigInteger.valueOf(0));
    accountEntity.setCurrencyCode(CURRENCY_CODE);
    accountEntity.setStatus(ACCOUNT_STATUS);
    accountEntity.setName(ACCOUNT_NAME);
    accountEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
    return accountRepository.save(accountEntity);
  }

  protected InitiateTransferBodyV1 getTransferBody(
      String sourceAccountId, String beneficiaryAccountId, String amount) {
    return new InitiateTransferBodyV1()
        .reference(REFERENCE)
        .amount(amount)
        .currencyCode(CURRENCY_CODE)
        .sourceAccount(UUID.fromString(sourceAccountId))
        .beneficiaryAccount(UUID.fromString(beneficiaryAccountId))
        .description(DESCRIPTION)
        .dateTime(DATE_TIME);
  }
}
