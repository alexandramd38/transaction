package transaction.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.transfer.api.model.AccountBalanceV1;
import com.transfer.api.model.AccountStatus;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transaction.repository.entity.AccountEntity;

@ExtendWith(MockitoExtension.class)
class AccountConverterTest {

  private static final String ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String ACCOUNT_NAME = "Current account";
  private static final UUID CUSTOMER_ID = UUID.randomUUID();
  private static final String AMOUNT = "10000";
  private static final String BLOCKED_AMOUNT = "0";
  private static final String CURRENCY_CODE = "AED";
  private static final Timestamp TIMESTAMP = Timestamp.from(Instant.now());

  @Mock private AccountEntity accountEntity;
  @InjectMocks private AccountConverter accountConverter;

  @Test
  void convert() {
    mockAccountEntity();
    AccountBalanceV1 accountBalanceV1 = accountConverter.convert(accountEntity);

    assertEquals(ACCOUNT_ID, accountBalanceV1.getAccountId());
    assertEquals(ACCOUNT_NAME, accountBalanceV1.getName());
    assertEquals(CUSTOMER_ID.toString(), accountBalanceV1.getCustomerId());
    assertEquals(AMOUNT, accountBalanceV1.getAccountBalance());
    assertEquals(AMOUNT, accountBalanceV1.getAvailableBalance());
    assertEquals("0", accountBalanceV1.getBlockedAccount());
    assertEquals(CURRENCY_CODE, accountBalanceV1.getCurrencyCode());
    assertEquals(AccountStatus.ACTIVE, accountBalanceV1.getAccountStatus());
    assertEquals(TIMESTAMP.toString(), accountBalanceV1.getTimestamp());
  }

  private void mockAccountEntity() {
    when(accountEntity.getAccountId()).thenReturn(ACCOUNT_ID);
    when(accountEntity.getName()).thenReturn(ACCOUNT_NAME);
    when(accountEntity.getCustomerId()).thenReturn(CUSTOMER_ID);
    when(accountEntity.getBalance()).thenReturn(new BigInteger(AMOUNT));
    when(accountEntity.getBlockedAmount()).thenReturn(new BigInteger(BLOCKED_AMOUNT));
    when(accountEntity.getCurrencyCode()).thenReturn(CURRENCY_CODE);
    when(accountEntity.getStatus()).thenReturn(AccountStatus.ACTIVE.getValue());
    when(accountEntity.getCreationDate()).thenReturn(TIMESTAMP);
  }
}
