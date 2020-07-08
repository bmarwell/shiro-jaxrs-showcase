package io.github.bmhm.shiro.jaxrs.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.env.IniWebEnvironment;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

@ApplicationPath("/")
public class JaxRsShiroApplication extends Application {

    public JaxRsShiroApplication() {

        // There are a few ways to configure Shiro, since there was an INI file in the project you could do this:

        // configure Shiro via INI
        IniWebEnvironment environment = new IniWebEnvironment();
        environment.init();
        SecurityManager securityManager = environment.getSecurityManager();

        // They way you performing a login, you would need to do something like this
        SecurityUtils.setSecurityManager(securityManager);

        // Another option is configure the SecurityManager as a bean (i'm not sure what liberty supports out of the box for injection)
        // it depends on what you are doing, but i'd usually suggest using a filtering technique to handle the login (similar to what we do for Servlets)
        // IIRC you would have some sort of ContainerRequestFilter, that would check for basic auth, bearer auth, cookie, etc, and create a Subject
        // and bind it to the request context.
        // the current `SubjectPrincipalRequestFilter` which uses `ShiroSecurityContext` assumes the subject is available on the current thread
        // this works fine in Servlet + JAX-RS environments (that are not async)

        // For example the Servlet integration basically creates a subject and then calls `subject.execute()`
        // see https://shiro.apache.org/subject.html#automatic-association
        // NOTE: this type of association may not be ideal for pure JAX-RS environments
    }
}
