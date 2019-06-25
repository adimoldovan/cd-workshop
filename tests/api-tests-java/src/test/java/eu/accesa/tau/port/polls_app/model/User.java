package eu.accesa.tau.port.polls_app.model;

import eu.accesa.tau.port.polls_app.util.TestUtils;

public class User {
    private String name;
    private String username;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getRandomUser()
    {
        User user = new User();

        String lastName = TestUtils.getRandomString(10, false);
        String username = String.format("s.%s", lastName);

        user.setName(String.format("Squirrel %s", lastName));
        user.setEmail(String.format("%s@mailinator.com", username));
        user.setUsername(username);
        user.setPassword("start123");
        return user;
    }
}
