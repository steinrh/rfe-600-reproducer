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
import java.util.Map;
import java.util.UUID;

@Path("/hello")
public class ExampleResource {
    static final Logger LOGGER = Logger.getLogger(ExampleResource.class);

    @Context
    HttpServerRequest request;

    @ConfigProperty(name = "login.url", defaultValue = "localhost:8080/login")
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


    @ConfigProperty(name = "pod.name", defaultValue = "pod-not-set")
    String podName;


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
        StringBuilder result = new StringBuilder();

        result.append("Pod: ").append(podName);

        if (request.cookieCount() > 0) {
            result.append("\nCookies:");

            for (Map.Entry<String, Cookie> entry : request.cookieMap().entrySet()) {
                String key = entry.getKey();
                Cookie cookie = entry.getValue();

                result.append("\n- name=").append(key).append(", value=").append(cookie.getValue());

                result.append(" (sameSite=").append(cookie.getSameSite())
                        .append(", domain=").append(cookie.getDomain())
                        .append(", path=").append(cookie.getPath())
                        .append(", httpOnly=").append(cookie.isHttpOnly())
                        .append(", secure=").append(cookie.isSecure())
                        .append(")");
            }
        }

        Cookie cookie = request.getCookie(cookieName);
        if (cookie != null) {
            return Response.ok().entity(result.toString()).build();
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