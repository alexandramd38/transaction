package transaction.service;

import com.transfer.api.model.AccountBalanceV1;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.converter.AccountConverter;
import transaction.exception.AccountDetailsNotFoundException;
import transaction.exception.InsufficientFundsException;
import transaction.repository.AccountRepository;
import transaction.repository.entity.AccountEntity;

@Service
public class AccountService {
  private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

  private final AccountConverter accountConverter;
  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountConverter accountConverter, AccountRepository accountRepository) {
    this.accountConverter = accountConverter;
    this.accountRepository = accountRepository;
  }

  public AccountBalanceV1 getAccountBalance(String accountId) {
    AccountEntity accountEntity =
        accountRepository
            .getAccountEntityByAccountId(accountId)
            .orElseThrow(
                () ->
                    new AccountDetailsNotFoundException("AccountId=" + accountId + " not found."));
    return accountConverter.convert(accountEntity);
  }

  public void moveMoney(
      String sourceAccountId, String beneficiaryAccountId, BigInteger amountToTransfer) {

    var sourceAccount =
        accountRepository
            .getAccountEntityByAccountIdForUpdate(sourceAccountId)
            .orElseThrow(
                () ->
                    new AccountDetailsNotFoundException(
                        "Source AccountId=" + sourceAccountId + " not found."));

    var beneficiaryAccount =
        accountRepository
            .getAccountEntityByAccountIdForUpdate(beneficiaryAccountId)
            .orElseThrow(
                () ->
                    new AccountDetailsNotFoundException(
                        "Beneficiary AccountId=" + beneficiaryAccountId + " not found."));

    logger.info(
        "Account details before processing. Source account balance = "
            + sourceAccount.getBalance()
            + ". Beneficiary account balance = "
            + beneficiaryAccount.getBalance());

    var sourceAvailableAmount =
        sourceAccount.getBalance().subtract(sourceAccount.getBlockedAmount());

    if (sourceAvailableAmount.compareTo(amountToTransfer) == -1) {
      throw new InsufficientFundsException(
          "Insufficient funds as available balance. availableBalance="
              + sourceAvailableAmount
              + "; amountToTransfer="
              + amountToTransfer);
    }

    sourceAccount.setBalance(sourceAccount.getBalance().subtract(amountToTransfer));
    beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(amountToTransfer));

    accountRepository.save(sourceAccount);
    accountRepository.save(beneficiaryAccount);
  }
}
