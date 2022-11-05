package transaction.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.transfer.api.controller.TransferApi;
import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController implements TransferApi {

  private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

  public ResponseEntity<TransferResponseV1> initiateTransfer(
      String jwt,
      String idempotencyKey,
      InitiateTransferBodyV1 initiateTransferBodyV1,
      String correlationId) {
    logger.info("Received initiate transfer request: " + initiateTransferBodyV1);
    return ResponseEntity.status(CREATED).body(new TransferResponseV1());
  }
}
