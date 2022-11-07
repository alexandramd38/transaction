package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import java.util.UUID;
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

  private static final UUID SOURCE_ACCOUNT = UUID.randomUUID();
  private static final UUID BENEFICIARY_ACCOUNT = UUID.randomUUID();

  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private TransferEntity transferEntity;
  @Mock private TransferResponseV1 transferResponseV1;

  @Mock private TransferConverter transferConverter;
  @Mock private TransferRepository transferRepository;
  @Mock private MoneyTransferService moneyTransferService;

  @InjectMocks private TransferService transferService;

  @Test
  void initiateTransfer() {
    when(transferRepository.save(transferEntity)).thenReturn(transferEntity);

    when(initiateTransferBodyV1.getSourceAccount()).thenReturn(SOURCE_ACCOUNT);
    when(initiateTransferBodyV1.getBeneficiaryAccount()).thenReturn(BENEFICIARY_ACCOUNT);

    when(moneyTransferService.transfer(
            initiateTransferBodyV1,
            transferEntity,
            SOURCE_ACCOUNT.toString(),
            BENEFICIARY_ACCOUNT.toString()))
        .thenReturn(transferEntity);
    when(transferConverter.convert(initiateTransferBodyV1)).thenReturn(transferEntity);
    when(transferConverter.convert(transferEntity)).thenReturn(transferResponseV1);

    assertEquals(transferResponseV1, transferService.initiateTransfer(initiateTransferBodyV1));

    verify(transferConverter, times(1)).convert(initiateTransferBodyV1);
    verify(transferRepository, times(1)).save(transferEntity);
    verify(initiateTransferBodyV1, times(1)).getSourceAccount();
    verify(initiateTransferBodyV1, times(1)).getBeneficiaryAccount();
    verify(moneyTransferService, times(1))
        .transfer(
            initiateTransferBodyV1,
            transferEntity,
            SOURCE_ACCOUNT.toString(),
            BENEFICIARY_ACCOUNT.toString());
    verify(transferConverter, times(1)).convert(transferEntity);
  }
}
