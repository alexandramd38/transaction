package transaction.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transaction.repository.entity.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

  Optional<AccountEntity> getAccountEntityByAccountId(String accountId);

  @Query(
      value = "SELECT * FROM {h-schema}account  where account_id=?1 for update",
      nativeQuery = true)
  Optional<AccountEntity> getAccountEntityByAccountIdForUpdate(String accountId);
}
