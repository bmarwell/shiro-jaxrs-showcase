package io.github.bmhm.shiro.jaxrs.login.dto;

import javax.json.bind.annotation.JsonbProperty;
import java.util.StringJoiner;

public class LoginData {

    @JsonbProperty("username")
    private String username;

    @JsonbProperty("password")
    private String password;

    public LoginData() {
    }

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LoginData.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
