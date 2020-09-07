package com.redhat.reproducer.rfe600;

import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

@Path("/hello")
public class ExampleResource {
    static final Logger LOGGER = Logger.getLogger(ExampleResource.class);

    @Context
    HttpServerRequest request;

    @ConfigProperty(name = "login.url", defaultValue = "localhost:8080/index.html")
    String redirectURL;

    @ConfigProperty(name = "cookie.name", defaultValue = "javasession")
    String cookieName;

    @ConfigProperty(name = "cookie.domain", defaultValue = "/")
    String domain;

    @ConfigProperty(name = "cookie.path", defaultValue = "/")
    String path;

    @ConfigProperty(name = "cookie.isSecure", defaultValue = "false")
    boolean isSecure;

    @ConfigProperty(name = "cookie.httpOnly", defaultValue = "false")
    boolean httpOnly;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHello() {
        return doTheWork();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response postHello() {
        return doTheWork();
    }

    public Response doTheWork() {
        Response.ResponseBuilder result;

        for (String cookieName : request.cookieMap().keySet()) {
            Cookie cookie = request.cookieMap().get(cookieName);

            LOGGER.infof("Cookie: name=%s, value=%s, domain=%s, path=%s, samesite=%s, isSecure=%b, httpOnly=%b",
                         cookieName, cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getSameSite(), cookie.isSecure(), cookie.isHttpOnly());
        }

        Cookie cookie = request.getCookie(cookieName);
        if (cookie != null) {
            return Response.ok().entity("Cookie: " + cookie.getName() + "=" + cookie.getValue()).build();
        } else {
            LOGGER.error("No cookie set!");

            NewCookie newCookie = new NewCookie(
                    cookieName,
                    UUID.randomUUID().toString(),
                    path,
                    domain,
                    "No comment",
                    86400,
                    isSecure,
                    httpOnly
            );

            return Response
                    .seeOther(URI.create(redirectURL))
                    .cookie(newCookie)
                    .build();
        }
    }
}