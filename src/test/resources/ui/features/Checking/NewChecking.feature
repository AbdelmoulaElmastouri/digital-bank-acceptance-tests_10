Feature: Creating a new checking account

  Scenario: Creat a standard individual checking account
    Given the user logged in as "abelee@gmail.com" "Nytaxi!@12"
    When the user creates a new checking account with the following data
      | checkingAccountType | accountOwnership | accountName             | initialDepositAmount |
      | Standard Checking   | Individual       | Abe Lee Second Checking | 100000.0             |
    Then the user should see the green "Successfully created new Standard Checking account named Abe Lee Second Checking" message
    And the use should see newly added account card
      | accountName             | accountType       | ownership  | accountNumber | interestRate | balance   |
      | Abe Lee Second Checking | Standard Checking | Individual | 486134042     | 0.0%         | 100000.00 |
    And the user should see the following transactions
      | date       | category | description               | amount   | balance  |
      | 2024-01-08 | Income   | 845325270 (DPT) - Deposit | 100000.0 | 100000.0 |