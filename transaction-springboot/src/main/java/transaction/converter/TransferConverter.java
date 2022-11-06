package transaction.converter;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.stereotype.Component;
import transaction.TransferStatus;
import transaction.repository.entity.TransferEntity;

@Component
public class TransferConverter {

  private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public TransferEntity convert(InitiateTransferBodyV1 transferBodyV1) {
    TransferEntity transferEntity = new TransferEntity();
    transferEntity.setReference(transferBodyV1.getReference());
    transferEntity.setTransferReference(UUID.randomUUID());
    transferEntity.setSourceAccountId(transferBodyV1.getSourceAccount());
    transferEntity.setBeneficiaryAccountId(transferBodyV1.getBeneficiaryAccount());
    transferEntity.setAmount(new BigInteger(transferBodyV1.getAmount()));
    transferEntity.setStatus(TransferStatus.INITIATED.name());
    transferEntity.setCurrencyCode(transferBodyV1.getCurrencyCode());
    transferEntity.setSubmissionTimestamp(transferBodyV1.getDateTime());
    transferEntity.setCreationTimestamp(
        now(UTC).format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));

    return transferEntity;
  }

  public TransferResponseV1 convert(TransferEntity entity) {
    TransferResponseV1 response = new TransferResponseV1();
    response.setTransferId(entity.getTransferReference().toString());
    response.setReference(entity.getReference());
    response.setAmount(entity.getAmount().toString());
    response.setCurrencyCode(entity.getCurrencyCode());
    response.setSourceAccount(entity.getSourceAccountId());
    response.setBeneficiaryAccount(entity.getBeneficiaryAccountId());
    response.setCreationDateTime(entity.getCreationTimestamp());
    response.setStatus(entity.getStatus());
    return response;
  }
}
