package io.github.bmhm.shiro.jaxrs.login;

import io.github.bmhm.shiro.jaxrs.login.dto.LoginData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

@Path("/user")
public class UserEndpoint {

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postLogin(LoginData loginData) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginData.getUsername(), loginData.getPassword());
            subject.login(token);

            return Response.accepted()
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (AuthenticationException authEx) {
            Map<String, Object> message = Collections.singletonMap("message", "unknown user or wrong password");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(message)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (RuntimeException rtEx) {
            throw new WebApplicationException(
                    "Problem with login: " + rtEx.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
