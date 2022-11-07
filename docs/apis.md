<table>
  <tr>
    <td><a href="overview.md">Previous</a></td>
    <td><a href="../README.md">Home</a></td>
  </tr>
</table>

## APIs definition

Transfer service exposes 2 endpoints:

- get account balance to retrieve the account details for a given account id
- initiate transfer API to transfer money between 2 accounts already registered in the system, having the same currency code

Transfer Service APIs are described in the following image:

![transfer-apis](images/transfer-apis.jpg)

API definition is exposed by the application at [/swagger-ui/index.html](http://localhost:8089/swagger-ui/index.html) endpoint.

### Get Account Balance API
Retrieves account details for the given account id provided by the customer. If provided account id is not present in the system, a 404 HTTP status code response is returned with a response message specifying that the account does not exist in the system.

**Endpoint:**
```
/account/{accountId}/balance
```
**Headers:**
```
Content-Type:application/v1+json
Accept:application/v1+json
jwt:eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NzYwNjUyMSwiaWF0IjoxNjY3NjA2NTIxfQ.MVez01NYx_dPmB39qYVL64LIyQO_mR73lR0zK2nlTHw
```
**HTTP Method:**
```
GET
```
**Request body:**
```
{}
```
**Response body example:**
```
{
    "accountId": "849beade-e6cd-4f9d-8379-072f6e04b649",
    "name": null,
    "customerId": "5ec7087b-06e3-41af-b80d-3acd618d9182",
    "accountBalance": "200000",
    "availableBalance": "200000",
    "blockedAccount": "0",
    "currencyCode": "EUR",
    "accountStatus": "ACTIVE",
    "timestamp": "2022-11-06 12:38:03.961374"
}
```

### Initiate transfer API

API used to transfer money from an account to another account, having the same currency code (it is currently ignored).

Transfer money flow:
- stores the transfer in _transfer_ table with _INITIATED_ status, for audit purposes
- check if source account exists
- check if beneficiary account exists
- check if there are enough money in source account (account balance and blocked amount are considered in validation)
- move money from source account to beneficiary account, if the above conditions are met, by updating _account_ table
- update transfer status in _transfer_ table from _INITIATED_ to _TRANSFERRED_

**Endpoint:**
```
/transfer
```
**Headers:**
```
Content-Type:application/v1+json
Accept:application/v1+json
jwt:eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NzYwNjUyMSwiaWF0IjoxNjY3NjA2NTIxfQ.MVez01NYx_dPmB39qYVL64LIyQO_mR73lR0zK2nlTHw
idempotencyKey:4564690a-4bea-11ec-81d3-0242ac130003
```
**HTTP Method:**
```
POST
```
**Request body example:**
```
{
    "reference": "0012567848",
    "amount": "1000000",
    "currencyCode": "EUR",
    "sourceAccount": "849beade-e6cd-4f9d-8379-072f6e04b649",
    "beneficiaryAccount": "30171b6e-5959-4b72-9c85-559575f36fe2",
    "description": "Money for lunch",
    "dateTime": "2022-11-07 15:25:55",
    "priority": "NORMAL"
}
```
**Response body example:**
```
{
    "transferId": "eaab60ae-5000-4343-b875-4837ad49a7f3",
    "reference": "0012567848",
    "amount": "1000",
    "currencyCode": "EUR",
    "sourceAccount": "849beade-e6cd-4f9d-8379-072f6e04b649",
    "beneficiaryAccount": "30171b6e-5959-4b72-9c85-559575f36fe2",
    "creationDateTime": "2022-11-07 00:30:01",
    "status": "TRANSFERRED"
}
```

**Response body example - In case of insufficient money in source account:**
```
{
    "code": "4002",
    "message": "Insufficient funds",
    "dateTime": "2022-11-07T00:35:39.8076695Z"
}
```
