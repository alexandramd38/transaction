package transaction.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.transfer.api.model.InitiateTransferBodyV1;
import com.transfer.api.model.TransferResponseV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import transaction.service.TransferService;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

  private static final String JWT = "JWT_TEST_VALUE";
  private static final String IDEMPOTENCY_KEY = "IDEMPOTENCY_KEY_TEST_VALUE";
  private static final String CORRELATION_ID = "CORRELATION_ID_TEST_VALUE";

  @Mock private InitiateTransferBodyV1 initiateTransferBodyV1;
  @Mock private TransferResponseV1 responseV1;
  @Mock private TransferService transferService;

  @InjectMocks private TransferController transferController;

  @Test
  void initiateTransfer() {
    when(transferService.initiateTransfer(initiateTransferBodyV1)).thenReturn(responseV1);
    ResponseEntity<TransferResponseV1> responseEntity =
        transferController.initiateTransfer(
            JWT, IDEMPOTENCY_KEY, initiateTransferBodyV1, CORRELATION_ID);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(responseV1, responseEntity.getBody());
  }
}
