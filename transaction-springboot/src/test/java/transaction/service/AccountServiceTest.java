package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.transfer.api.model.AccountBalanceV1;
import java.math.BigInteger;
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
import transaction.exception.InsufficientFundsException;
import transaction.repository.AccountRepository;
import transaction.repository.entity.AccountEntity;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  private static final String ACCOUNT_ID = UUID.randomUUID().toString();
  private static final String BENEFICIARY_ACCOUNT_ID = UUID.randomUUID().toString();
  private static final BigInteger AMOUNT_TO_TRANSFER = BigInteger.valueOf(100);

  @Mock private AccountConverter accountConverter;
  @Mock private AccountRepository accountRepository;
  @Mock private AccountEntity accountEntity;
  @Mock private AccountEntity beneficiaryAccountEntity;
  @Mock private AccountBalanceV1 accountBalanceV1;

  @InjectMocks private AccountService accountService;

  @Test
  void getAccountBalance() {
    when(accountRepository.getAccountEntityByAccountId(ACCOUNT_ID))
        .thenReturn(Optional.of(accountEntity));
    when(accountConverter.convert(accountEntity)).thenReturn(accountBalanceV1);

    assertEquals(accountBalanceV1, accountService.getAccountBalance(ACCOUNT_ID));
  }

  @Test
  void getAccountBalanceWhenInvalidAccountId() {
    when(accountRepository.getAccountEntityByAccountId(ACCOUNT_ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        AccountDetailsNotFoundException.class, () -> accountService.getAccountBalance(ACCOUNT_ID));
  }

  @Test
  void moveMoney() {

    when(accountRepository.getAccountEntityByAccountIdForUpdate(ACCOUNT_ID))
        .thenReturn(Optional.of(accountEntity));
    when(accountRepository.getAccountEntityByAccountIdForUpdate(BENEFICIARY_ACCOUNT_ID))
        .thenReturn(Optional.of(beneficiaryAccountEntity));

    when(accountEntity.getBalance()).thenReturn(AMOUNT_TO_TRANSFER);
    when(accountEntity.getBlockedAmount()).thenReturn(BigInteger.valueOf(0));
    when(beneficiaryAccountEntity.getBalance()).thenReturn(BigInteger.valueOf(0));

    when(accountRepository.save(accountEntity)).thenReturn(accountEntity);
    when(accountRepository.save(beneficiaryAccountEntity)).thenReturn(beneficiaryAccountEntity);

    accountService.moveMoney(ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER);

    verify(accountRepository).getAccountEntityByAccountIdForUpdate(ACCOUNT_ID);
    verify(accountRepository).getAccountEntityByAccountIdForUpdate(BENEFICIARY_ACCOUNT_ID);
    verify(accountEntity, times(3)).getBalance();
    verify(accountEntity).getBlockedAmount();

    verify(accountEntity).setBalance(BigInteger.valueOf(0));
    verify(beneficiaryAccountEntity).setBalance(AMOUNT_TO_TRANSFER);

    verify(accountRepository).save(accountEntity);
    verify(accountRepository).save(beneficiaryAccountEntity);
  }

  @Test
  void moveMoneyWhenInvalidSourceAccount() {
    when(accountRepository.getAccountEntityByAccountIdForUpdate(ACCOUNT_ID))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(
        AccountDetailsNotFoundException.class,
        () -> accountService.moveMoney(ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER));
  }

  @Test
  void moveMoneyWhenInvalidBeneficiaryAccount() {

    when(accountRepository.getAccountEntityByAccountIdForUpdate(ACCOUNT_ID))
        .thenReturn(Optional.of(accountEntity));
    when(accountRepository.getAccountEntityByAccountIdForUpdate(BENEFICIARY_ACCOUNT_ID))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(
        AccountDetailsNotFoundException.class,
        () -> accountService.moveMoney(ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER));
  }

  @Test
  void moveMoneyWhenInvalidInsufficientFunds() {

    when(accountRepository.getAccountEntityByAccountIdForUpdate(ACCOUNT_ID))
        .thenReturn(Optional.of(accountEntity));
    when(accountRepository.getAccountEntityByAccountIdForUpdate(BENEFICIARY_ACCOUNT_ID))
        .thenReturn(Optional.of(beneficiaryAccountEntity));

    when(accountEntity.getBalance()).thenReturn(BigInteger.valueOf(10));
    when(accountEntity.getBlockedAmount()).thenReturn(BigInteger.valueOf(0));

    Assertions.assertThrows(
        InsufficientFundsException.class,
        () -> accountService.moveMoney(ACCOUNT_ID, BENEFICIARY_ACCOUNT_ID, AMOUNT_TO_TRANSFER));
  }
}
