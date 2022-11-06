package transaction.service;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.TransferStatus;
import transaction.converter.TransferConverter;
import transaction.repository.TransferRepository;
import transaction.repository.entity.TransferEntity;

@Service
public class TransferService {

  private final TransferConverter transferConverter;
  private final TransferRepository transferRepository;
  private final AccountService accountService;

  @Autowired
  public TransferService(
      TransferConverter transferConverter,
      TransferRepository transferRepository,
      AccountService accountService) {
    this.transferConverter = transferConverter;
    this.transferRepository = transferRepository;
    this.accountService = accountService;
  }

  public TransferResponseV1 initiateTransfer(InitiateTransferBodyV1 transferBodyV1) {
    var entity = transferRepository.save(transferConverter.convert(transferBodyV1));
    var sourceAccountId = transferBodyV1.getSourceAccount().toString();
    var beneficiaryAccountId = transferBodyV1.getBeneficiaryAccount().toString();

    return transferConverter.convert(
        transfer(transferBodyV1, entity, sourceAccountId, beneficiaryAccountId));
  }

  @Transactional
  protected TransferEntity transfer(
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
