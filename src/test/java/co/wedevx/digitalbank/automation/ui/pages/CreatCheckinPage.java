package co.wedevx.digitalbank.automation.ui.pages;

import co.wedevx.digitalbank.automation.ui.models.NewCheckingAccountInfo;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatCheckinPage extends BaseMenuPage {

    public CreatCheckinPage (WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "new-account-conf-alert")
    private WebElement newAccountConfAlertDiv;

    @FindBy(id = "Standard Checking")
    private WebElement standardCheckingAccountTypeRadioButton;

    @FindBy(id = "Interest Checking")
    private WebElement interestCheckingAccountTypeRadioButton;

    @FindBy(id = "Individual")
    private WebElement IndividualOwnershipTypeRadioButton;

    @FindBy(id = "Joint")
    private WebElement JointOwnershipTypeRadioButton;

    @FindBy(id = "name")
    private WebElement accountNameTxt;

    @FindBy(id = "openingBalance")
    private WebElement openingBalanceTxtBox;

    @FindBy(id = "newCheckingSubmit")
    private WebElement submit;


    public void createNewChecking(List<NewCheckingAccountInfo> checkingAccountInfoList) {

        NewCheckingAccountInfo testDataForOneCheckingAccount = checkingAccountInfoList.get(0);

        // Click on checking button
        checkingMenu.click();

        // Click on the new checking button
        newCheckingButton.click();

        assertEquals(ConfigReader.getPropertiesValue("digitalbank.createnewcheckingurl"), getDriver().getCurrentUrl(),"new checking Button didn't take to the " + ConfigReader.getPropertiesValue("digitalbank.createnewcheckingurl"));


        // Select the account type
        if (testDataForOneCheckingAccount.getCheckingAccountType().equalsIgnoreCase("Standard Checking")) {
            standardCheckingAccountTypeRadioButton.click();
        }
        else if (testDataForOneCheckingAccount.getCheckingAccountType().equalsIgnoreCase("Interest Checking")) {
            interestCheckingAccountTypeRadioButton.click();
        }
        else
        {
            throw new NoSuchElementException("Invalid Checking Account Type. Only support Standard Checking and Individual checking");
        }

        if (testDataForOneCheckingAccount.getAccountOwnership().equalsIgnoreCase("Individual"))
        {
            IndividualOwnershipTypeRadioButton.click();
        }
        else if (testDataForOneCheckingAccount.getAccountOwnership().equalsIgnoreCase("Joint"))
        {
            JointOwnershipTypeRadioButton.click();
        }
        else
        {
            throw new NoSuchElementException("Invalid ownership Type provided. Only support Individual Checking and Joint checking");
        }


        // Enter the account name
        accountNameTxt.sendKeys(testDataForOneCheckingAccount.getAccountName());

        // Enter the initial deposit amount
        openingBalanceTxtBox.sendKeys(String.valueOf(testDataForOneCheckingAccount.getInitialDepositAmount()));

        // Click on the submit button
        submit.click();
    }

    }



