package com.redhat.reproducer.rfe600;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

/**
 * @author rlichti
 * @version 1.0.0 2020-09-07
 * @since 1.0.0 2020-09-07
 */
@Provider
public class CookiePrinter implements ContainerRequestFilter {
    private static final Logger LOG = Logger.getLogger(CookiePrinter.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @ConfigProperty(name = "pod.name", defaultValue = "pod-not-set")
    String podName;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MDC.put("pod", podName);

        final String method = requestContext.getMethod();
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();

        LOG.infof("===== Request %s %s from IP %s", method, path, address);

        if (request.cookieCount() > 0) {
            for (Map.Entry<String, io.vertx.core.http.Cookie> entry : request.cookieMap().entrySet()) {
                String key = entry.getKey();
                io.vertx.core.http.Cookie cookie = entry.getValue();

                LOG.infof("Cookie: name=%s, value=%s, domain=%s, path=%s, sameSite=%b, httpOnly=%b, isSecure=%b",
                        entry.getKey(),
                        cookie.getValue(), cookie.getDomain(), cookie.getPath(),
                        cookie.getSameSite(), cookie.isHttpOnly(), cookie.isSecure());
            }
        }
    }
}
