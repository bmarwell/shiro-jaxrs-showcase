package io.github.bmhm.shiro.jaxrs.login;

import io.github.bmhm.shiro.jaxrs.login.dto.LoginData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UserEndpointIT {

    private static final JsonbConfig JSONB_CONFIG = new JsonbConfig()
            .withNullValues(true);

    private static final Jsonb JSONB = JsonbBuilder.newBuilder()
            .withConfig(JSONB_CONFIG)
            .build();

    private static String URL;

    @BeforeAll
    public static void init() {
        String port = System.getProperty("http.port");
        URL = "http://localhost:" + port + "/" + System.getProperty("war.name");
    }

    @Test
    public void testLoginSuccess() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(URL + "/user/login");

        LoginData loginData = new LoginData("admin", "admin");
        String httpPostAdmin = JSONB.toJson(loginData);
        method.setRequestEntity(new StringRequestEntity(httpPostAdmin, MediaType.APPLICATION_JSON, StandardCharsets.UTF_8.displayName()));

        try {
            int statusCode = client.executeMethod(method);

            assertEquals(HttpStatus.SC_ACCEPTED, statusCode, "HTTP POST failed");
        } finally {
            method.releaseConnection();
        }
    }

    @Test
    public void testLoginFail() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(URL + "/user/login");

        LoginData loginData = new LoginData("fail", "me");
        String httpPostAdmin = JSONB.toJson(loginData);
        method.setRequestEntity(new StringRequestEntity(httpPostAdmin, MediaType.APPLICATION_JSON, StandardCharsets.UTF_8.displayName()));

        try {
            int statusCode = client.executeMethod(method);

            assertEquals(HttpStatus.SC_UNAUTHORIZED, statusCode, "HTTP POST should have failed");
        } finally {
            method.releaseConnection();
        }
    }

}
