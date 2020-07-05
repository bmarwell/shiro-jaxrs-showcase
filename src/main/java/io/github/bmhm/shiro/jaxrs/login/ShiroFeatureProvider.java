package io.github.bmhm.shiro.jaxrs.login;

import org.apache.shiro.web.jaxrs.ShiroFeature;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class ShiroFeatureProvider extends ShiroFeature implements Feature {

    public ShiroFeatureProvider() {
        super();
    }

    @Override
    public boolean configure(FeatureContext context) {
        return super.configure(context);
    }
}
