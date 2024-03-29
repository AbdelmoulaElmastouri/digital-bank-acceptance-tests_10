//package co.wedevx.digitalbank.automation.ui.steps;
//
//import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
//import co.wedevx.digitalbank.automation.ui.utils.Driver;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//
//import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
//
//public class Hooks {
//
//    @Before("@Registration")
//    public void establishConnectionToDB() {
//        DBUtils.establishConnection();
//    }
//
//    @Before("not @Registration")
//    public void the_user_is_on_dbank_homepage() {
//        getDriver().get("https://dbank-qa.wedevx.co/bank/login");
//    }
//
//    @After("not @NegativeRegistrationCases")
//    public void afterEachScenario(Scenario scenario) {
//        Driver.takeScreenShot(scenario);
//        Driver.closeDriver();
//    }
//
//    @After()
//    public static void closeConnectionToDB() {
//        DBUtils.closeConnection();
//    }
//}


package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;

public class Hooks {

    @Before("@Registration")
    public void establishConnectionToDB() {
        DBUtils.establishConnection();
    }

    @Before("not @Registration")
    public void the_user_on_dbank_homepage() {
        getDriver().get("https://dbank-qa.wedevx.co/bank/login");
    }

    @After("NegativeRegistrationCases")
    public void afterEachScenario(Scenario scenario) {
        Driver.takeScreenShot(scenario);
        Driver.closeDriver();
    }

    @AfterAll()
    public static void closeConnectionToDB() {
        DBUtils.closeConnection();
    }
}
