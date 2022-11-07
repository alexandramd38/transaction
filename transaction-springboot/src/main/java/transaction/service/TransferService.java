package transaction.service;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.converter.TransferConverter;
import transaction.repository.TransferRepository;

@Service
public class TransferService {

  private final TransferConverter transferConverter;
  private final TransferRepository transferRepository;
  private final MoneyTransferService moneyTransferService;

  @Autowired
  public TransferService(
      TransferConverter transferConverter,
      TransferRepository transferRepository,
      MoneyTransferService moneyTransferService) {
    this.transferConverter = transferConverter;
    this.transferRepository = transferRepository;
    this.moneyTransferService = moneyTransferService;
  }

  public TransferResponseV1 initiateTransfer(InitiateTransferBodyV1 transferBodyV1) {
    var entity = transferRepository.save(transferConverter.convert(transferBodyV1));
    var sourceAccountId = transferBodyV1.getSourceAccount().toString();
    var beneficiaryAccountId = transferBodyV1.getBeneficiaryAccount().toString();

    return transferConverter.convert(
        moneyTransferService.transfer(
            transferBodyV1, entity, sourceAccountId, beneficiaryAccountId));
  }
}
