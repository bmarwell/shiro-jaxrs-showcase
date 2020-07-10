package io.github.bmhm.shiro.jaxrs.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.env.IniWebEnvironment;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * This class actually loads the security manager into the current thread context.
 * It is not intended to be injected anywhere.
 */
@Provider
@ApplicationScoped
public class ShiroSecurityManagerProvider implements ContextResolver<SecurityManager> {

    @Override
    public SecurityManager getContext(Class<?> type) {
        SecurityManager securityManager = EnvironmentHolder.ENVIRONMENT.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }

    static class EnvironmentHolder {
        static final Environment ENVIRONMENT = createEnvironment();

        private static Environment createEnvironment() {
            Ini shiroIni = new Ini();
            shiroIni.loadFromPath("classpath:/shiro.ini");
            IniWebEnvironment environment = new IniWebEnvironment();
            environment.setIni(shiroIni);
            environment.init();

            return environment;
        }
    }
}
