package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.transfer.api.model.AccountBalanceV1;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transaction.converter.AccountConverter;
import transaction.exception.AccountDetailsNotFoundException;
import transaction.repository.AccountRepository;
import transaction.repository.entity.AccountEntity;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  private static final String ACCOUNT_ID = UUID.randomUUID().toString();

  @Mock private AccountConverter accountConverter;
  @Mock private AccountRepository accountRepository;
  @Mock private AccountEntity accountEntity;
  @Mock private AccountBalanceV1 accountBalanceV1;

  @InjectMocks private AccountService accountService;

  @Test
  void getAccountBalance() {
    when(accountRepository.getAccountEntitiesByAccountId(ACCOUNT_ID))
        .thenReturn(Optional.of(accountEntity));
    when(accountConverter.convert(accountEntity)).thenReturn(accountBalanceV1);

    assertEquals(accountBalanceV1, accountService.getAccountBalance(ACCOUNT_ID));
  }

  @Test
  void getAccountBalanceWhenInvalidAccountId() {
    when(accountRepository.getAccountEntitiesByAccountId(ACCOUNT_ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        AccountDetailsNotFoundException.class, () -> accountService.getAccountBalance(ACCOUNT_ID));
  }
}
