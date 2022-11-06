package transaction.service;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.converter.TransferConverter;
import transaction.repository.TransferRepository;
import transaction.repository.entity.TransferEntity;

@Service
public class TransferService {

  private final TransferConverter transferConverter;
  private final TransferRepository transferRepository;

  @Autowired
  public TransferService(
      TransferRepository transferRepository, TransferConverter transferConverter) {
    this.transferRepository = transferRepository;
    this.transferConverter = transferConverter;
  }

  public TransferResponseV1 initiateTransfer(InitiateTransferBodyV1 transferBodyV1) {
    TransferEntity entity = transferConverter.convert(transferBodyV1);
    return transferConverter.convert(transferRepository.save(entity));
  }
}
