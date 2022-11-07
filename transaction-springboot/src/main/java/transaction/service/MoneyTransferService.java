package transaction.service;

import com.transfer.api.model.InitiateTransferBodyV1;
import java.math.BigInteger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import transaction.model.TransferStatus;
import transaction.repository.TransferRepository;
import transaction.repository.entity.TransferEntity;

@Component
public class MoneyTransferService {

  private final AccountService accountService;
  private final TransferRepository transferRepository;

  public MoneyTransferService(
      final AccountService accountService, final TransferRepository transferRepository) {
    this.accountService = accountService;
    this.transferRepository = transferRepository;
  }

  @Transactional
  public TransferEntity transfer(
      InitiateTransferBodyV1 transferBodyV1,
      TransferEntity entity,
      String sourceAccountId,
      String beneficiaryAccountId) {
    accountService.moveMoney(
        sourceAccountId, beneficiaryAccountId, new BigInteger(transferBodyV1.getAmount()));
    entity.setStatus(TransferStatus.TRANSFERRED.name());
    return transferRepository.save(entity);
  }
}
