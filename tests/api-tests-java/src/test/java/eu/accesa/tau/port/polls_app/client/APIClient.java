package eu.accesa.tau.port.polls_app.client;

import eu.accesa.tau.port.polls_app.configuration.Environment;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class APIClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIClient.class.getName());
    @Autowired
    private Environment environment;

    public APIResponse get(String path) throws IOException {
        HttpGet httpMethod = new HttpGet(environment.getApiBaseUrl() + path);
        return callApi(httpMethod);
    }

    public APIResponse post(String path, String requestBody) throws IOException {
        HttpPost httpMethod = new HttpPost(environment.getApiBaseUrl() + path);
        httpMethod.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        BasicHeader header = new BasicHeader("Authorization", environment.getJwtToken());
        httpMethod.addHeader(header);
        return callApi(httpMethod);
    }

    public APIResponse put(String path, String requestBody) throws IOException {
        HttpPut httpMethod = new HttpPut(environment.getApiBaseUrl() + path);
        httpMethod.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        BasicHeader header = new BasicHeader("Authorization", environment.getJwtToken());
        httpMethod.addHeader(header);
        return callApi(httpMethod);
    }

    public APIResponse delete(String path) throws IOException {
        HttpDelete httpMethod = new HttpDelete(environment.getApiBaseUrl() + path);
        BasicHeader header = new BasicHeader("Authorization", environment.getJwtToken());
        httpMethod.addHeader(header);
        return callApi(httpMethod);
    }

    private HttpClient getClient() {
        return HttpClientBuilder.create().build();
    }

    private APIResponse getAPIResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();
        LOGGER.debug("Response was: " + statusCode + " -" + reasonPhrase);
        InputStream bodyAsInputStream = httpResponse.getEntity().getContent();
        String responseBody = IOUtils.toString(bodyAsInputStream);
        LOGGER.debug("Response body: " + responseBody);
        httpResponse.getFirstHeader("Authorization");
        return new APIResponse(statusCode, reasonPhrase, responseBody, httpResponse.getAllHeaders());
    }

    private APIResponse callApi(HttpUriRequest request) throws IOException {
        LOGGER.debug(String.format("%s %s", request.getMethod(), request.getURI()));
        for (Header header : request.getAllHeaders()) {
            LOGGER.debug(String.format("%s %s", header.getName(), header.getValue()));
        }

        try {
            HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) request;
            LOGGER.debug(IOUtils.toString(httpEntityEnclosingRequest.getEntity().getContent()));
        } catch (ClassCastException ex) {
            LOGGER.error("Cannot cast...");
        }

        HttpResponse httpResponse = getClient().execute(request);
        return getAPIResponse(httpResponse);
    }
}
