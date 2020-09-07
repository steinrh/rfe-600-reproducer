package com.redhat.reproducer.rfe600;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.Map;

/**
 * @author rlichti
 * @version 1.0.0 2020-09-07
 * @since 1.0.0 2020-09-07
 */
public class CookiePrinter implements ContainerRequestFilter {
    private static final Logger LOG = Logger.getLogger(CookiePrinter.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        final String method = requestContext.getMethod();
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();

        LOG.infof("===== Request %s %s from IP %s", method, path, address);
    }
}
