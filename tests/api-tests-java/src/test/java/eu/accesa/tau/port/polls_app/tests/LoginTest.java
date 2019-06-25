package eu.accesa.tau.port.polls_app.tests;

import eu.accesa.tau.port.polls_app.service.AuthenticationApiService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginTest extends BaseTest {
    @Autowired
    AuthenticationApiService authenticationApiService;

//    @Test(description = "Test the sign up method with valid user details")
//    public void loginTestValid() throws IOException {
//        APIResponse apiResponse = authenticationApiService.signin(User.getRandomUser());
//
//        Assert.assertEquals(apiResponse.getStatusCode(), 201, "Check response status code");
//
//        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());
//
//        Assert.assertTrue(jsonObject.getBoolean("success"), "Check success field in response body");
//        Assert.assertEquals(jsonObject.getString("message"), "User registered successfully", "Check message in response body");
//    }
//
//    @Test(description = "Test the sign up method with an existing username")
//    public void signUpTestExistingUsername() throws IOException {
//        User user = User.getRandomUser();
//        user.setUsername("s.squirrel");
//        APIResponse apiResponse = authenticationApiService.signup(user);
//
//        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Check response status code");
//
//        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());
//
//        Assert.assertFalse(jsonObject.getBoolean("success"), "Check success field in response body");
//        Assert.assertEquals(jsonObject.getString("message"), "Username is already taken!", "Check message in response body");
//    }

}
