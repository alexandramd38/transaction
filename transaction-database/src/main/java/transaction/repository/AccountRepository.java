package transaction.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transaction.repository.entity.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {
  Optional<AccountEntity> getAccountEntitiesByAccountId(String accountId);
}
