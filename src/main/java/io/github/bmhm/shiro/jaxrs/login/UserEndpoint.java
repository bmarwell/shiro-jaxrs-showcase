package io.github.bmhm.shiro.jaxrs.login;

import io.github.bmhm.shiro.jaxrs.login.dto.LoginData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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
  public Response postLogin(final LoginData loginData, @Context final HttpServletRequest httpServletRequest) {
    try {
      final Subject subject = SecurityUtils.getSubject();
      final Session session = subject.getSession(true);
      final UsernamePasswordToken token = new UsernamePasswordToken(loginData.getUsername(), loginData.getPassword());
      token.setHost(httpServletRequest.getRemoteHost());
      token.setRememberMe(true);
      System.out.println("remote host:" + token.getHost());
      subject.login(token);

      return Response.ok()
              .type(MediaType.APPLICATION_JSON_TYPE)
              .build();
    } catch (final AuthenticationException authEx) {
      final Map<String, Object> message = Collections.singletonMap("message", "unknown user or wrong password");
      return Response.status(Response.Status.UNAUTHORIZED)
              .entity(message)
              .type(MediaType.APPLICATION_JSON_TYPE)
              .build();
    } catch (final RuntimeException rtEx) {
      throw new WebApplicationException(
              "Problem with login: " + rtEx.getMessage(),
              Response.Status.INTERNAL_SERVER_ERROR);
    }
  }
}
