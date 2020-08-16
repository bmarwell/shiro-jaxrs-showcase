package io.github.bmhm.shiro.jaxrs.login;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import io.github.bmhm.shiro.jaxrs.login.dto.LoginData;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost method = new HttpPost(URL + "/user/login");

        LoginData loginData = new LoginData("admin", "admin");
        String httpPostAdmin = JSONB.toJson(loginData);
        final BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(new ByteArrayInputStream(httpPostAdmin.getBytes(StandardCharsets.UTF_8)));
        entity.setContentEncoding("UTF-8");
        entity.setContentType(MediaType.APPLICATION_JSON);
        method.setEntity(entity);

        try {
            CloseableHttpResponse response = client.execute(method);

            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "HTTP POST failed");
            System.out.println(Arrays.asList(response.getAllHeaders()));
        } finally {
            method.releaseConnection();
        }
    }

    @Test
    public void testLoginFail() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost method = new HttpPost(URL + "/user/login");

        LoginData loginData = new LoginData("fail", "me");
        String httpPostAdmin = JSONB.toJson(loginData);
        final BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(new ByteArrayInputStream(httpPostAdmin.getBytes(StandardCharsets.UTF_8)));
        entity.setContentEncoding("UTF-8");
        entity.setContentType(MediaType.APPLICATION_JSON);
        method.setEntity(entity);
        try {
            CloseableHttpResponse response = client.execute(method);

            assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusLine().getStatusCode(), "HTTP POST failed");
        } finally {
            method.releaseConnection();
        }
    }

}
