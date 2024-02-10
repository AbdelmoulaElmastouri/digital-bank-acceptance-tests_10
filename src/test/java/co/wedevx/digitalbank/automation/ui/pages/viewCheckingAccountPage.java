package co.wedevx.digitalbank.automation.ui.pages;

import co.wedevx.digitalbank.automation.ui.models.AccountCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewCheckingAccountPage extends BaseMenuPage {
    public viewCheckingAccountPage (WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "new-account-conf-alert")
    private WebElement newAccountConfAlertDiv;

    @FindBy(xpath = "//div[@id= 'firstRow']/div")
    private List<WebElement> allFirstRowDivs;

    @FindBy(xpath = "//table[@id= 'transactionTable']/tbody/tr")
    private WebElement firstRawOfTransactions;

    public Map<String , String> getNewlyCheckingAccountTransactionInfoMap() {

        List<WebElement> firstRawColumns = firstRawOfTransactions.findElements(By.xpath("td"));
        String actualCategory = firstRawColumns.get(1).getText();
        String actualDescription = firstRawColumns.get(2).getText();
        double actualAmount = Double.parseDouble(firstRawColumns.get(3).getText().substring(1));
        double actualBalance = Double.parseDouble(firstRawColumns.get(4).getText().substring(1));

        Map<String, String> actualResultMap = new HashMap<>();
        actualResultMap.put("actualCategory", firstRawColumns.get(1).getText());
        actualResultMap.put("actualDescription", firstRawColumns.get(2).getText());
        actualResultMap.put("actualAmount", firstRawColumns.get(3).getText().substring(1));
        actualResultMap.put("actualBalance", firstRawColumns.get(4).getText().substring(1));
        return actualResultMap;

    }

    public Map<String , String> getNewlyCheckingAccountInfoMap() {
        WebElement lastAccountCart = allFirstRowDivs.get(allFirstRowDivs.size() - 1);
        String actualResult = lastAccountCart.getText();

        Map<String,String> actualResultMap = new HashMap<>();
        actualResultMap.put("actualAccountName", actualResult.substring(0, actualResult.indexOf("\n")));
        actualResultMap.put("actualAccountType", actualResult.substring(actualResult.indexOf("Account"), actualResult.indexOf("Ownership")).trim());
        actualResultMap.put("actualOwnership", actualResult.substring(actualResult.indexOf("Ownership:"), actualResult.indexOf("Account Number:")).trim());
        actualResultMap.put("actualAccountNumber", actualResult.substring(actualResult.indexOf("Account Number:"), actualResult.indexOf("Interest Rate:")).trim());
        actualResultMap.put("actualInterestRate", actualResult.substring(actualResult.indexOf("Interest Rate:"), actualResult.indexOf("Balance:")).trim());
        actualResultMap.put("actualBalance", actualResult.substring(actualResult.indexOf("Balance:")).trim());

        return actualResultMap;

    }

    public String getActualCreateAccountConfirmationMessage() {
        return newAccountConfAlertDiv.getText();
    }

}
