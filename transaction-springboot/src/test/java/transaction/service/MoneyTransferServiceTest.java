package transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.transfer.api.model.InitiateTransferBodyV1;
import java.math.BigInteger;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import transaction.model.TransferStatus;
import transaction.repository.TransferRepository;
import transaction.repository.entity.TransferEntity;

@ExtendWith(MockitoExtension.class)
class MoneyTransferServiceTest {

  private static final String SOURCE_ACCOUNT = UUID.randomUUID().toString();
  private static final String BENEFICIARY_ACCOUNT = UUID.randomUUID().toString();
  private static final String AMOUNT = "100";

  @Mock private TransferEntity entity;
  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private AccountService accountService;
  @Mock private TransferRepository transferRepository;

  @InjectMocks private MoneyTransferService moneyTransferService;

  @Test
  void transfer() {
    when(initiateTransferBodyV1.getAmount()).thenReturn(AMOUNT);
    doNothing().when(accountService).moveMoney(anyString(), anyString(), any(BigInteger.class));
    when(transferRepository.save(entity)).thenReturn(entity);

    assertEquals(
        entity,
        moneyTransferService.transfer(
            initiateTransferBodyV1, entity, SOURCE_ACCOUNT, BENEFICIARY_ACCOUNT));
    verify(initiateTransferBodyV1, times(1)).getAmount();
    verify(accountService, times(1))
        .moveMoney(SOURCE_ACCOUNT, BENEFICIARY_ACCOUNT, new BigInteger(AMOUNT));
    verify(transferRepository, times(1)).save(entity);
    verify(entity).setStatus(TransferStatus.TRANSFERRED.name());
  }
}
