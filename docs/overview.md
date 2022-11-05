<table>
  <tr>
    <td><a href="../README.md">Home</a></td>
    <td><a href="apis.md">Next</a></td>
  </tr>
</table>

## Overview

Handles account to account transactions, for the same currency code accounts.
Currency code is not validated at this point.

### Database structure
The following database structure is created when application is starting:

![database-structure](images/database-structure.jpg)

A H2 database is used, and it is created using liquibase based on the changelog defined in transaction-database module.