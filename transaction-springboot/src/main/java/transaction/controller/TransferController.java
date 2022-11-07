package transaction.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.transfer.api.controller.TransferApi;
import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import transaction.service.TransferService;

@RestController
public class TransferController implements TransferApi {

  private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

  private final TransferService transferService;

  @Autowired
  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }

  public ResponseEntity<TransferResponseV1> initiateTransfer(
      String jwt,
      String idempotencyKey,
      InitiateTransferBodyV1 initiateTransferBodyV1,
      String correlationId) {
    logger.info("Received initiate transfer request: " + initiateTransferBodyV1);
    TransferResponseV1 response = transferService.initiateTransfer(initiateTransferBodyV1);
    logger.info(
        "Request for reference = "
            + initiateTransferBodyV1.getReference()
            + " was successfully processed");
    return ResponseEntity.status(CREATED).body(response);
  }
}
