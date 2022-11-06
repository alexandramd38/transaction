package transaction.repository.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "account")
public class AccountEntity {
  @Id
  @Column(
      name = "id",
      updatable = false,
      nullable = false,
      columnDefinition = "uuid default random_uuid()")
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String accountId;
  private UUID customerId;
  private BigInteger balance;
  private BigInteger blockedAmount;
  private String currencyCode;
  private String status;
  private String accountType;
  private String name;
  private String accountCode;
  private Timestamp creationDate;
  private Timestamp dateLastUpdated;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public BigInteger getBalance() {
    return balance;
  }

  public void setBalance(BigInteger balance) {
    this.balance = balance;
  }

  public BigInteger getBlockedAmount() {
    return blockedAmount;
  }

  public void setBlockedAmount(BigInteger blockedAmount) {
    this.blockedAmount = blockedAmount;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccountCode() {
    return accountCode;
  }

  public void setAccountCode(String accountCode) {
    this.accountCode = accountCode;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public Timestamp getDateLastUpdated() {
    return dateLastUpdated;
  }

  public void setDateLastUpdated(Timestamp dateLastUpdated) {
    this.dateLastUpdated = dateLastUpdated;
  }
}
