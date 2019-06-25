package eu.accesa.tau.port.polls_app.configuration;

public class Environment {
    private String apiBaseUrl;
    private String dbBaseUrl;
    private String dbUser;
    private String dbPassword;
    private String jwtToken;
    private boolean cleanData;

    public boolean isCleanData() {
        return cleanData;
    }

    public void setCleanData(boolean cleanData) {
        this.cleanData = cleanData;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getDbBaseUrl() {
        return dbBaseUrl;
    }

    public void setDbBaseUrl(String dbBaseUrl) {
        this.dbBaseUrl = dbBaseUrl;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
