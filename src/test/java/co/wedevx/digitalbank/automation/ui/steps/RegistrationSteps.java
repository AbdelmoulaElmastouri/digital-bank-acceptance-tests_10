package co.wedevx.digitalbank.automation.ui.steps;
import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationSteps {

    RegistrationPage registrationPage = new RegistrationPage(getDriver());

    @Given("User navigates to Digital Bank signup page")
    public void user_navigates_to_digital_bank_signup_page() {
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.registrationpageurl"));
        assertEquals("Digital Bank", getDriver().getTitle(), "Registration page title mismatch");
    }

    @When("User creates account following fields with mocked email and ssn")
    public void user_creates_account_following_fields_with_mocked_email_and_ssn(List<Map<String, String>> registrationTestDataListMap) {
        registrationPage.fillOutRegistrationForm(registrationTestDataListMap);
    }

    @Then("User should be displayed with the message {string}")
    public void user_should_be_displayed_with_the_message(String expectedSuccessMessage) {
        String actualSuccessMessage = registrationPage.getMessage();
        assertEquals(expectedSuccessMessage, actualSuccessMessage, "SuccessMessage mismatch");
    }

    @When("User creates account with following fields with mocked email and ssn")
    public void user_creates_account_with_following_fields_with_mocked_email_and_ssn(List<Map<String, String>> registrationTestDataListMap)  {
        registrationPage.fillOutRegistrationForm(registrationTestDataListMap);
    }

    @Then("the user should see the {string} following required field error message {string}")
    public void the_user_should_see_the_following_required_field_error_message(String fieldWithError, String errorMessage) {
        String actualErrorMessage = registrationPage.getRequiredFieldErrorMessage(fieldWithError);
        assertEquals(errorMessage, actualErrorMessage, "Error message mismatch for field: " + fieldWithError);
    }
}


