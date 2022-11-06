package transaction.converter;

import com.transfer.api.model.AccountBalanceV1;
import com.transfer.api.model.AccountStatus;
import org.springframework.stereotype.Component;
import transaction.repository.entity.AccountEntity;

@Component
public class AccountConverter {

  public AccountBalanceV1 convert(AccountEntity accountEntity) {
    AccountBalanceV1 account = new AccountBalanceV1();
    account.setAccountId(accountEntity.getAccountId());
    account.setName(accountEntity.getName());
    account.setCustomerId(accountEntity.getCustomerId().toString());
    account.setAccountBalance(accountEntity.getBalance().toString());
    account.setAvailableBalance(
        accountEntity.getBalance().subtract(accountEntity.getBlockedAmount()).toString());
    account.setBlockedAmount(accountEntity.getBlockedAmount().toString());
    account.setCurrencyCode(accountEntity.getCurrencyCode());
    account.setAccountStatus(AccountStatus.fromValue(accountEntity.getStatus()));
    account.setTimestamp(accountEntity.getCreationDate().toString());
    return account;
  }
}
