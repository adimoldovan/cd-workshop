package eu.accesa.tau.port.polls_app.type;

public enum Tables {
    USERS("users"),
    USER_ROLES("user_roles");

    private String tableName;

    Tables(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
