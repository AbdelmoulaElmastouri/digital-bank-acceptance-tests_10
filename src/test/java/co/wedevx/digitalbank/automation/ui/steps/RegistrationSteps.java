package co.wedevx.digitalbank.automation.ui.steps;
import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegistrationSteps {

    RegistrationPage registrationPage = new RegistrationPage(getDriver());

    @Given("User navigates to Digital Bank signup page")
    public void user_navigates_to_digital_bank_signup_page() {
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.registrationpageurl"));
        assertEquals("Digital Bank", getDriver().getTitle(), "Registration page title mismatch");
    }

    @When("User creates account following with fields")
    public void user_creates_account_with_following_fields(List<Map<String, String>> registrationTestDataListMap) {
        registrationPage.fillOutRegistrationForm(registrationTestDataListMap);
    }

    @Then("User should be displayed with the message {string}")
    public void user_should_be_displayed_with_the_message(String expectedSuccessMessage) {
        String actualSuccessMessage = registrationPage.getMessage();
        assertEquals(expectedSuccessMessage, actualSuccessMessage, "SuccessMessage mismatch");
    }

    @When("User creates account with following fields")
    public void user_creates_account_following_fields(List<Map<String, String>> registrationTestDataListMap)  {
        registrationPage.fillOutRegistrationForm(registrationTestDataListMap);
    }

    @Then("the user should see the {string} following required field error message {string}")
    public void theUserShouldSeeTheFollowingRequiredFieldErrorMessage(String fieldWithError, String errorMessage) {
        String actualErrorMessage = registrationPage.getRequiredFieldErrorMessage(fieldWithError);
        assertEquals(errorMessage, actualErrorMessage, "Error message mismatch for field: " + fieldWithError);
    }

    @Then("the following user info should be saved in the db")
    public void theFollowingUserInfoShouldBeSavedInTheDb(List<Map<String , String >> expectedUserProfileInfoInDBList) {
        Map<String, String> expectedUserInfoMap = expectedUserProfileInfoInDBList.get(0);
     String queryUserTable = String.format("Select * from users where username= '%s'", expectedUserInfoMap.get("email"));
        String queryUserProfile = String.format("Select * from user_profile where email_address= '%s'", expectedUserInfoMap.get("email"));

        List<Map<String, Object>> actualUserInfoList = DBUtils.runSQLSelectQuery(queryUserTable);
        List<Map<String, Object>> actualUserProfileInfoList = DBUtils.runSQLSelectQuery(queryUserProfile);

        assertEquals(1, actualUserInfoList.size(), "registration generate unexpected number of users");
        assertEquals(1, actualUserProfileInfoList.size(), "registration generate unexpected number of users profiles");

        Map<String, Object> actualUserInfoMap = actualUserInfoList.get(0);
        Map<String, Object> actualUserProfileInfoMap = actualUserProfileInfoList.get(0);

        assertEquals(expectedUserInfoMap.get("title"), actualUserProfileInfoMap.get("title"),"registration generate wrong title");
        assertEquals(expectedUserInfoMap.get("firstName"), actualUserProfileInfoMap.get("first_name"),"registration generate wrong firstName");
        assertEquals(expectedUserInfoMap.get("lastName"), actualUserProfileInfoMap.get("last_name"),"registration generate wrong lastName");
        assertEquals(expectedUserInfoMap.get("gender"), actualUserProfileInfoMap.get("gender"),"registration generate wrong gender");
        assertEquals(expectedUserInfoMap.get("dob"), actualUserProfileInfoMap.get("dob"),"registration generate wrong dob");
        assertEquals(expectedUserInfoMap.get("ssn"), actualUserProfileInfoMap.get("ssn"),"registration generate wrong ssn");
        assertEquals(expectedUserInfoMap.get("email"), actualUserProfileInfoMap.get("email_address"),"registration generate wrong email");
        assertEquals(expectedUserInfoMap.get("address"), actualUserProfileInfoMap.get("address"),"registration generate wrong address");
        assertEquals(expectedUserInfoMap.get("locality"), actualUserProfileInfoMap.get("locality"),"registration generate wrong locality");
        assertEquals(expectedUserInfoMap.get("region"), actualUserProfileInfoMap.get("region"),"registration generate wrong region");
        assertEquals(expectedUserInfoMap.get("postalCode"), actualUserProfileInfoMap.get("postal_code"),"registration generate wrong postalCode");
        assertEquals(expectedUserInfoMap.get("country"), actualUserProfileInfoMap.get("country"),"registration generate wrong country");
        assertEquals(expectedUserInfoMap.get("homePhone"), actualUserProfileInfoMap.get("home_phone"),"registration generate wrong homePhone");
        assertEquals(expectedUserInfoMap.get("mobilePhone"), actualUserProfileInfoMap.get("mobile_phone"),"registration generate wrong mobilePhone");
        assertEquals(expectedUserInfoMap.get("workPhone"), actualUserProfileInfoMap.get("work_phone"),"registration generate wrong workPhone");

     //validate users table
        assertEquals(expectedUserInfoMap.get("accountNonExpired"), String.valueOf(actualUserInfoMap.get("account_non_expired")),"accountNonExpired mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("accountNonLocked"), String.valueOf(actualUserInfoMap.get("account_non_locked")),"accountNonLocked mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("credentialsNonExpired"), String.valueOf(actualUserInfoMap.get("credentials_non_expired")),"credentialsNonExpired  mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("enabled"), String.valueOf(actualUserInfoMap.get("enabled")),"account enabled  mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("email"), actualUserInfoMap.get("username"),"username mismatch upon registration");

    }

    @Given("The user with {string} is not in DB")
    public void theUserWithIsNotInDB(String email) {
        String queryForUserProfile = String.format("DELETE FROM user_profile WHERE email_address ='%s'", email);
        String queryForUsers= String.format("DELETE FROM users WHERE username ='%s'", email);
        DBUtils.runSQLUpdateQuery(queryForUserProfile);
        DBUtils.runSQLUpdateQuery(queryForUsers);
    }


}



