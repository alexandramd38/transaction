package transaction.repository.entity;

import java.math.BigInteger;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "transfer")
public class TransferEntity {

  @Id
  @Column(
      name = "id",
      updatable = false,
      nullable = false,
      columnDefinition = "uuid default random_uuid()")
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String reference;
  private UUID transferReference;
  private UUID sourceAccountId;
  private UUID beneficiaryAccountId;
  private BigInteger amount;
  private String status;
  private String currencyCode;
  private String submissionTimestamp;
  private String creationTimestamp;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public UUID getTransferReference() {
    return transferReference;
  }

  public void setTransferReference(UUID transferReference) {
    this.transferReference = transferReference;
  }

  public UUID getSourceAccountId() {
    return sourceAccountId;
  }

  public void setSourceAccountId(UUID sourceAccountId) {
    this.sourceAccountId = sourceAccountId;
  }

  public UUID getBeneficiaryAccountId() {
    return beneficiaryAccountId;
  }

  public void setBeneficiaryAccountId(UUID beneficiaryAccountId) {
    this.beneficiaryAccountId = beneficiaryAccountId;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public void setAmount(BigInteger amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getSubmissionTimestamp() {
    return submissionTimestamp;
  }

  public void setSubmissionTimestamp(String submissionTimestamp) {
    this.submissionTimestamp = submissionTimestamp;
  }

  public String getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(String creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
}
