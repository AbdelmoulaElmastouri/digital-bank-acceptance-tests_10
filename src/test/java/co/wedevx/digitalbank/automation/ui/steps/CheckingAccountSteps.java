package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.AccountCard;
import co.wedevx.digitalbank.automation.ui.models.BankTransaction;
import co.wedevx.digitalbank.automation.ui.models.NewCheckingAccountInfo;
import co.wedevx.digitalbank.automation.ui.pages.LoginPage;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public  class CheckingAccountSteps {

    WebDriver driver = Driver.getDriver();

    private LoginPage loginPage = new LoginPage(driver);



    @Given("the user logged in as {string} {string}")
    public void the_user_logged_in_as(String username, String password) {
        loginPage.login(username, password);
    }

    @When("the user creates a new checking account with the following data")
    public void the_user_creates_a_new_checking_account_with_the_following_data(List<NewCheckingAccountInfo> checkingAccountInfoList) {
        NewCheckingAccountInfo testDataForOneCheckingAccount = checkingAccountInfoList.get(0);

        // Click on checking button
        WebElement checkingMenu = driver.findElement(By.id("checking-menu"));
        checkingMenu.click();

        // Click on the new checking button
        WebElement newCheckingButton = driver.findElement(By.id("new-checking-menu-item"));
        newCheckingButton.click();

        String expectedUrl = "https://dbank-qa.wedevx.co/bank/account/checking-add";
        assertEquals(expectedUrl, driver.getCurrentUrl(), "new checking Button didn't take to the " + expectedUrl);


        // Select the account type
        WebElement accountTypeRadioButton = driver.findElement(By.id(testDataForOneCheckingAccount.getCheckingAccountType()));
        accountTypeRadioButton.click();

        // Select Account Ownership
        WebElement ownershipTypeRadioButton = driver.findElement(By.id(testDataForOneCheckingAccount.getAccountOwnership()));
        ownershipTypeRadioButton.click();

        // Enter the account name
        WebElement accountNameTxt = driver.findElement(By.id("name"));
        accountNameTxt.sendKeys(testDataForOneCheckingAccount.getAccountName());

        // Enter the initial deposit amount
        WebElement openingBalanceTxtBox = driver.findElement(By.id("openingBalance"));
        openingBalanceTxtBox.sendKeys(String.valueOf(testDataForOneCheckingAccount.getInitialDepositAmount()));

        // Click on the submit button
        WebElement submit = driver.findElement(By.id("newCheckingSubmit"));
        submit.click();
    }

    @Then("the user should see the green {string} message")
    public void the_user_should_see_the_green_message(String expectedConfMessage) {
        //new-account-msg
        WebElement newAccountConfAlertDiv = driver.findElement(By.id("new-account-conf-alert"));
        expectedConfMessage = "Confirmation " + expectedConfMessage + "\n×";
        assertEquals(expectedConfMessage, newAccountConfAlertDiv.getText());

   }

    @Then("the use should see newly added account card")
    public void the_use_should_see_newly_added_account_card(List<AccountCard> accountCardList) {

        List<WebElement> allFirstRowDivs = driver.findElements(By.xpath("//div[@id= 'firstRow']/div"));
        //We did this to get the last cart.
        WebElement lastAccountCart = allFirstRowDivs.get(allFirstRowDivs.size() - 1);
        String actualResult = lastAccountCart.getText();
        String actualAccountName = actualResult.substring(0, actualResult.indexOf("\n"));
        String actualAccountType = actualResult.substring(actualResult.indexOf("Account"), actualResult.indexOf("Ownership")).trim();
        String actualOwnership = actualResult.substring(actualResult.indexOf("Ownership:"), actualResult.indexOf("Account Number:")).trim();
        String actualAccountNumber = actualResult.substring(actualResult.indexOf("Account Number:"), actualResult.indexOf("Interest Rate:")).trim();
        String actualInterestRate = actualResult.substring(actualResult.indexOf("Interest Rate:"), actualResult.indexOf("Balance:")).trim();
        String actualBalance = actualResult.substring(actualResult.indexOf("Balance:"));

        AccountCard expectedResult = accountCardList.get(0);
        assertEquals(expectedResult.getAccountName(), actualAccountName);
        assertEquals("Account: " + expectedResult.getAccountType(), actualAccountType);
        assertEquals("Ownership: " + expectedResult.getOwnership(), actualOwnership);
        assertEquals("Interest Rate: " + expectedResult.getInterestRate(), actualInterestRate);

        String expectedBalance = String.format("%.2f", expectedResult.getBalance());
        assertEquals("Balance: $" + expectedBalance, actualBalance);

    }

    @Then("the user should see the following transactions")
    public void the_user_should_see_the_following_transactions(List<BankTransaction> expectedTransactions) {

        WebElement firstRawOfTransactions = driver.findElement(By.xpath("//table[@id= 'transactionTable']/tbody/tr"));

        List<WebElement> firstRawColumns = firstRawOfTransactions.findElements(By.xpath("td"));

        String actualCategory = firstRawColumns.get(1).getText();
        String actualDescription = firstRawColumns.get(2).getText();
        double actualAmount = Double.parseDouble(firstRawColumns.get(3).getText().substring(1));
        double actualBalance = Double.parseDouble(firstRawColumns.get(4).getText().substring(1));

        BankTransaction ExpectedTransaction = expectedTransactions.get(0);

        assertEquals(ExpectedTransaction.getCategory(), actualCategory, "transaction category mismatch");
        //assertEquals(expectedTransactions.getDescription(), actualDescription, "transaction description mismatch");
        assertEquals(ExpectedTransaction.getAmount(), actualAmount, "transaction amount mismatch");
        assertEquals(ExpectedTransaction.getBalance(), actualBalance, "transaction balance mismatch");

    }
}