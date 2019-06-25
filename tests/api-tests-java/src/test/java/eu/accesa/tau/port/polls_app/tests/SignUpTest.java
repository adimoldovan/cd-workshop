package eu.accesa.tau.port.polls_app.tests;

import eu.accesa.tau.port.polls_app.client.APIResponse;
import eu.accesa.tau.port.polls_app.model.User;
import eu.accesa.tau.port.polls_app.service.AuthenticationApiService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class SignUpTest extends BaseTest {
    @Autowired
    AuthenticationApiService authenticationApiService;

    @Test(description = "Test the sign up method with valid user details")
    public void signUpTestValid() throws IOException {
        APIResponse apiResponse = authenticationApiService.signup(User.getRandomUser());

        Assert.assertEquals(apiResponse.getStatusCode(), 201, "Check response status code");

        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());

        Assert.assertTrue(jsonObject.getBoolean("success"), "Check success field in response body");
        Assert.assertEquals(jsonObject.getString("message"), "User registered successfully", "Check message in response body");
    }

    @Test(description = "Test the sign up method with an existing username")
    public void signUpTestExistingUsername() throws IOException {
        User user = User.getRandomUser();
        user.setUsername("s.squirrel");
        APIResponse apiResponse = authenticationApiService.signup(user);

        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Check response status code");

        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());

        Assert.assertFalse(jsonObject.getBoolean("success"), "Check success field in response body");
        Assert.assertEquals(jsonObject.getString("message"), "Username is already taken!", "Check message in response body");
    }

    @Test(description = "Test the sign up method with an existing email address")
    public void signUpTestExistingEmail() throws IOException {
        User user = User.getRandomUser();
        user.setEmail("s.squirrel@mailinator.com");
        APIResponse apiResponse = authenticationApiService.signup(user);

        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Check response status code");

        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());

        Assert.assertFalse(jsonObject.getBoolean("success"), "Check success field in response body");
        Assert.assertEquals(jsonObject.getString("message"), "Email Address already in use!", "Check message in response body");
    }

    @Test(description = "Test the sign up method with an username that's too short")
    public void signUpTestShortUsername() throws IOException {
        User user = User.getRandomUser();
        user.setUsername("ab");
        APIResponse apiResponse = authenticationApiService.signup(user);
        assertShortFields(apiResponse);
    }

    @Test(description = "Test the sign up method with a name that's too short")
    public void signUpTestShortName() throws IOException {
        User user = User.getRandomUser();
        user.setName("abc");
        APIResponse apiResponse = authenticationApiService.signup(user);
        assertShortFields(apiResponse);
    }

    @Test(description = "Test the sign up method with an invalid email address (missing @)")
    public void signUpTestInvalidEmailMissingAt() throws IOException {
        User user = User.getRandomUser();
        user.setEmail("abc");
        APIResponse apiResponse = authenticationApiService.signup(user);
        assertShortFields(apiResponse);
    }

    @Test(description = "Test the sign up method with an invalid email address (missing domain)")
    public void signUpTestInvalidEmailMissingDomain() throws IOException {
        User user = User.getRandomUser();
        user.setEmail("abc@");
        APIResponse apiResponse = authenticationApiService.signup(user);
        assertShortFields(apiResponse);
    }

    @Test(description = "Test the sign up method with an invalid email address (missing local part)")
    public void signUpTestInvalidEmailMissingLocalPart() throws IOException {
        User user = User.getRandomUser();
        user.setEmail("@mailinator.com");
        APIResponse apiResponse = authenticationApiService.signup(user);
        assertShortFields(apiResponse);
    }

    private void assertShortFields(APIResponse apiResponse) {
        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Check response status code");
        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());
        Assert.assertEquals(jsonObject.getString("message"), "Validation failed for object='signUpRequest'. Error count: 1", "Check message in response body");
    }
}
