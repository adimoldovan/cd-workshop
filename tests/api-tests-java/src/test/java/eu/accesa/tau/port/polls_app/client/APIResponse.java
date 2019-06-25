package eu.accesa.tau.port.polls_app.client;

import org.apache.http.Header;

public class APIResponse {
    private int statusCode;
    private String reasonPhrase;
    private String responseBody;
    private Header[] headers;

    public APIResponse() {
    }

    public APIResponse(int statusCode, String reasonPhrase, String responseBody, Header[] headers) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.responseBody = responseBody;
        this.headers = headers;
    }

    public Header getHeader(String headerName) {
        for (Header header : this.headers) {
            if (header.getName().equalsIgnoreCase(headerName)) {
                return header;
            }
        }
        return null;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
