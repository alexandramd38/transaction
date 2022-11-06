package transaction.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import transaction.repository.entity.TransferEntity;

@ExtendWith(MockitoExtension.class)
class TransferConverterTest {

  private static final String REFERENCE = "TEST_REFERENCE";
  private static final UUID TRANSFER_REFERENCE = UUID.randomUUID();
  private static final UUID SOURCE_ACCOUNT = UUID.randomUUID();
  private static final UUID BENEFICIARY_ACCOUNT = UUID.randomUUID();
  private static final String AMOUNT = "1000";
  private static final String CURRENCY_CODE = "EUR";
  private static final String DATE_TIME = "2022-12-06 13:12:11";
  private static final String STATUS = "INITIATED";

  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private TransferEntity entity;

  @InjectMocks private TransferConverter transferConverter;

  @Test
  void convertToEntity() {
    mockInitiateTransferBodyV1();
    TransferEntity transferEntity = transferConverter.convert(initiateTransferBodyV1);

    assertEquals(REFERENCE, transferEntity.getReference());
    assertNotNull(transferEntity.getTransferReference());
    assertEquals(SOURCE_ACCOUNT, transferEntity.getSourceAccountId());
    assertEquals(BENEFICIARY_ACCOUNT, transferEntity.getBeneficiaryAccountId());
    assertEquals(new BigInteger(AMOUNT), transferEntity.getAmount());
    assertEquals(STATUS, transferEntity.getStatus());
    assertEquals(CURRENCY_CODE, transferEntity.getCurrencyCode());
    assertEquals(DATE_TIME, transferEntity.getSubmissionTimestamp());
    assertNotNull(transferEntity.getCreationTimestamp());
  }

  @Test
  void convertToTransferResponseV1() {
    mockTransferEntity();
    TransferResponseV1 responseV1 = transferConverter.convert(entity);

    assertEquals(TRANSFER_REFERENCE.toString(), responseV1.getTransferId());
    assertEquals(REFERENCE, responseV1.getReference());
    assertEquals(AMOUNT, responseV1.getAmount());
    assertEquals(CURRENCY_CODE, responseV1.getCurrencyCode());
    assertEquals(SOURCE_ACCOUNT, responseV1.getSourceAccount());
    assertEquals(BENEFICIARY_ACCOUNT, responseV1.getBeneficiaryAccount());
    assertEquals(DATE_TIME, responseV1.getCreationDateTime());
    assertEquals(STATUS, responseV1.getStatus());
  }

  private void mockInitiateTransferBodyV1() {
    when(initiateTransferBodyV1.getReference()).thenReturn(REFERENCE);
    when(initiateTransferBodyV1.getSourceAccount()).thenReturn(SOURCE_ACCOUNT);
    when(initiateTransferBodyV1.getBeneficiaryAccount()).thenReturn(BENEFICIARY_ACCOUNT);
    when(initiateTransferBodyV1.getAmount()).thenReturn(AMOUNT);
    when(initiateTransferBodyV1.getCurrencyCode()).thenReturn(CURRENCY_CODE);
    when(initiateTransferBodyV1.getDateTime()).thenReturn(DATE_TIME);
  }

  private void mockTransferEntity() {
    when(entity.getTransferReference()).thenReturn(TRANSFER_REFERENCE);
    when(entity.getReference()).thenReturn(REFERENCE);
    when(entity.getAmount()).thenReturn(new BigInteger(AMOUNT));
    when(entity.getCurrencyCode()).thenReturn(CURRENCY_CODE);
    when(entity.getSourceAccountId()).thenReturn(SOURCE_ACCOUNT);
    when(entity.getBeneficiaryAccountId()).thenReturn(BENEFICIARY_ACCOUNT);
    when(entity.getCreationTimestamp()).thenReturn(DATE_TIME);
    when(entity.getStatus()).thenReturn(STATUS);
  }
}
