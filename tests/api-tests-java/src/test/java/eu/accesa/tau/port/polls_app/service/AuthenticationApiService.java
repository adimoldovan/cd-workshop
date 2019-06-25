package eu.accesa.tau.port.polls_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.accesa.tau.port.polls_app.client.APIClient;
import eu.accesa.tau.port.polls_app.client.APIResponse;
import eu.accesa.tau.port.polls_app.model.Credentials;
import eu.accesa.tau.port.polls_app.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationApiService {
    private final String BASE_PATH = "/auth";
    private final String SIGNIN_PATH = BASE_PATH + "/signin";
    private final String SIGNUP_PATH = BASE_PATH + "/signup";

    @Autowired
    APIClient apiClient;

    public APIResponse signup(String requestBody) throws IOException {
        return apiClient.post(SIGNUP_PATH, requestBody);
    }

    public APIResponse signup(User user) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return signup(om.writeValueAsString(user));
    }

    public APIResponse signin(String requestBody) throws IOException {
        return apiClient.post(SIGNIN_PATH, requestBody);
    }

    public APIResponse signin(Credentials credentials) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return signin(om.writeValueAsString(credentials));
    }

    public String getJwtToken(Credentials credentials) throws IOException {
        APIResponse apiResponse = signin(credentials);
        JSONObject jsonObject = new JSONObject(apiResponse.getResponseBody());
        return jsonObject.getString("accessToken");
    }
}
