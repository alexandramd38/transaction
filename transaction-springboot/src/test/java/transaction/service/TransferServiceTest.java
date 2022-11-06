package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transaction.converter.TransferConverter;
import transaction.repository.TransferRepository;
import transaction.repository.entity.TransferEntity;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

  @Mock private TransferConverter transferConverter;
  @Mock private TransferRepository transferRepository;
  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private TransferEntity transferEntity;
  @Mock private TransferResponseV1 transferResponseV1;

  @InjectMocks private TransferService transferService;

  @Test
  void initiateTransfer() {
    when(transferConverter.convert(initiateTransferBodyV1)).thenReturn(transferEntity);
    when(transferRepository.save(transferEntity)).thenReturn(transferEntity);
    when(transferConverter.convert(transferEntity)).thenReturn(transferResponseV1);

    TransferResponseV1 responseV1 = transferService.initiateTransfer(initiateTransferBodyV1);

    assertEquals(transferResponseV1, responseV1);
  }
}
