package transaction.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import transaction.repository.entity.TransferEntity;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, UUID> {}
