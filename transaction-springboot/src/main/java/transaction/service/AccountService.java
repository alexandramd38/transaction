package transaction.service;

import com.transfer.api.model.AccountBalanceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.converter.AccountConverter;
import transaction.exception.AccountDetailsNotFoundException;
import transaction.repository.AccountRepository;
import transaction.repository.entity.AccountEntity;

@Service
public class AccountService {

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
            .getAccountEntitiesByAccountId(accountId)
            .orElseThrow(
                () ->
                    new AccountDetailsNotFoundException("AccountId=" + accountId + " not found."));
    return accountConverter.convert(accountEntity);
  }
}
