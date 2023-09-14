package starter.stepdef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import starter.utils.GorestAPI;
import starter.utils.Responses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class GorestStepDef {
    @Steps
    GorestAPI gorestAPI;
    @Given("Create a new user with valid json {string}")
    public void createANewUserWithValidJson(String jsonFile) {
        File json = new File(GorestAPI.REQ_BODY+jsonFile);
        gorestAPI.createANewUser(json);
    }

    @When("Send request create new user valid req body")
    public void sendRequestCreateNewUser() {
        Response response = SerenityRest.when().post(GorestAPI.CREATE_USER);
        JsonPath jsonPathEvaluator = response.jsonPath();
        GorestAPI.USER_ID = jsonPathEvaluator.get("id");
    }

    @When("Send request create new user invalid req body")
    public void sendRequestCreateNewUserInvalidReqBody() {
        SerenityRest.when().post(GorestAPI.CREATE_USER);
    }

    @Then("Status code should be {int}")
    public void statusCodeShouldBe(int statusCode) {
        SerenityRest.then().statusCode(statusCode);
    }

    @And("Response body should be matched with json {string}")
    public void responseBodyNameWasGenderWasAndEmailWas(String jsonFile) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(GorestAPI.REQ_BODY+jsonFile));
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get(Responses.NAME);
        String gender = (String) jsonObject.get(Responses.GENDER);
        String email = (String) jsonObject.get(Responses.EMAIL);
        SerenityRest.and()
                .body(Responses.NAME, equalTo(name))
                .body(Responses.GENDER, equalTo(gender))
                .body(Responses.EMAIL, equalTo(email));
    }

    @And("Response body should be displayed message {string}")
    public void responseBodyShouldBeDisplayedMessage(String message) {
        SerenityRest.and().body(Responses.MESSAGE, equalTo(message));
    }


    @Given("Get user detail with valid id")
    public void getUserDetailWithValidId() {
        gorestAPI.getDetailUser(GorestAPI.USER_ID);
    }

    @When("Send request get detail user")
    public void sendRequestGetDetailUser() {
        SerenityRest.when().get(GorestAPI.GET_USER_DETAIL);
    }

    @Given("Get user detail with valid id {string}")
    public void getUserDetailWithValidId(String id) {
        gorestAPI.getDetailUser(id);
    }

    @Given("Update user with valid id and request body {string}")
    public void updateUserWithValidIdAndRequestBody(String jsonFile) {
        File json = new File(GorestAPI.REQ_BODY+jsonFile);
        gorestAPI.updateUser(GorestAPI.USER_ID,json);
    }

    @When("Send request update user")
    public void sendRequestUpdateUser() {
        SerenityRest.when().put(GorestAPI.UPDATE_USER);
    }

    @Given("Delete user with valid user id")
    public void deleteUserWithValidUserId() {
        gorestAPI.deleteUser(GorestAPI.USER_ID);
    }

    @When("Send request delete user")
    public void sendRequestDeleteUser() {
        SerenityRest.when().delete(GorestAPI.DELETE_USER);
    }

    @Given("Delete user with not found user id {}")
    public void deleteUserWithNotFoundUserId(Object id) {
        gorestAPI.deleteUser(id);
    }
}
