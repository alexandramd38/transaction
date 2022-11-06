package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import java.math.BigInteger;
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
  private static final String AMOUNT = "100";

  @Mock private TransferConverter transferConverter;
  @Mock private TransferRepository transferRepository;
  @Mock private AccountService accountService;
  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private TransferEntity transferEntity;
  @Mock private TransferResponseV1 transferResponseV1;

  @InjectMocks private TransferService transferService;

  @Test
  void initiateTransfer() {
    mockInitiateTransferBodyV1();

    when(transferRepository.save(transferEntity)).thenReturn(transferEntity);
    when(transferConverter.convert(initiateTransferBodyV1)).thenReturn(transferEntity);
    when(transferConverter.convert(transferEntity)).thenReturn(transferResponseV1);

    doNothing().when(accountService).moveMoney(anyString(), anyString(), any(BigInteger.class));

    TransferResponseV1 responseV1 = transferService.initiateTransfer(initiateTransferBodyV1);

    assertEquals(transferResponseV1, responseV1);
  }

  private void mockInitiateTransferBodyV1() {
    when(initiateTransferBodyV1.getSourceAccount()).thenReturn(SOURCE_ACCOUNT);
    when(initiateTransferBodyV1.getBeneficiaryAccount()).thenReturn(BENEFICIARY_ACCOUNT);
    when(initiateTransferBodyV1.getAmount()).thenReturn(AMOUNT);
  }
}
