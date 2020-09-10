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

@Path("/login")
public class LoginPage {
    static final Logger LOGGER = Logger.getLogger(LoginPage.class);

    @ConfigProperty(name = "cookie.domain", defaultValue = "/")
    String domain;

    @ConfigProperty(name = "resource.port", defaultValue = "")
    String port;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getHello() {
        return doTheWork();
    }

    public Response doTheWork() {
        return Response
                .ok()
                .cookie(NewCookie.valueOf("login=true"))
                .entity("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>rfe600-cookielogger - 1.0.0-SNAPSHOT</title>\n" +
                        "    <style>\n" +
                        "        h1, h2, h3, h4, h5, h6 {\n" +
                        "            margin-bottom: 0.5rem;\n" +
                        "            font-weight: 400;\n" +
                        "            line-height: 1.5;\n" +
                        "        }\n" +
                        "\n" +
                        "        h1 {\n" +
                        "            font-size: 2.5rem;\n" +
                        "        }\n" +
                        "\n" +
                        "        h2 {\n" +
                        "            font-size: 2rem\n" +
                        "        }\n" +
                        "\n" +
                        "        h3 {\n" +
                        "            font-size: 1.75rem\n" +
                        "        }\n" +
                        "\n" +
                        "        h4 {\n" +
                        "            font-size: 1.5rem\n" +
                        "        }\n" +
                        "\n" +
                        "        h5 {\n" +
                        "            font-size: 1.25rem\n" +
                        "        }\n" +
                        "\n" +
                        "        h6 {\n" +
                        "            font-size: 1rem\n" +
                        "        }\n" +
                        "\n" +
                        "        .lead {\n" +
                        "            font-weight: 300;\n" +
                        "            font-size: 2rem;\n" +
                        "        }\n" +
                        "\n" +
                        "        .banner {\n" +
                        "            font-size: 2.7rem;\n" +
                        "            margin: 0;\n" +
                        "            padding: 2rem 1rem;\n" +
                        "            background-color: #00A1E2;\n" +
                        "            color: white;\n" +
                        "        }\n" +
                        "\n" +
                        "        body {\n" +
                        "            margin: 0;\n" +
                        "            font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\";\n" +
                        "        }\n" +
                        "\n" +
                        "        code {\n" +
                        "            font-family: SFMono-Regular, Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace;\n" +
                        "            font-size: 87.5%;\n" +
                        "            color: #e83e8c;\n" +
                        "            word-break: break-word;\n" +
                        "        }\n" +
                        "\n" +
                        "        .left-column {\n" +
                        "            padding: .75rem;\n" +
                        "            max-width: 75%;\n" +
                        "            min-width: 55%;\n" +
                        "        }\n" +
                        "\n" +
                        "        .right-column {\n" +
                        "            padding: .75rem;\n" +
                        "            max-width: 25%;\n" +
                        "        }\n" +
                        "\n" +
                        "        .container {\n" +
                        "            display: flex;\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "\n" +
                        "        li {\n" +
                        "            margin: 0.75rem;\n" +
                        "        }\n" +
                        "\n" +
                        "        .right-section {\n" +
                        "            margin-left: 1rem;\n" +
                        "            padding-left: 0.5rem;\n" +
                        "        }\n" +
                        "\n" +
                        "        .right-section h3 {\n" +
                        "            padding-top: 0;\n" +
                        "            font-weight: 200;\n" +
                        "        }\n" +
                        "\n" +
                        "        .right-section ul {\n" +
                        "            border-left: 0.3rem solid #00A1E2;\n" +
                        "            list-style-type: none;\n" +
                        "            padding-left: 0;\n" +
                        "        }\n" +
                        "\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<div class=\"banner lead\">\n" +
                        "    Your new Cloud-Native application is ready!\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"container\">\n" +
                        "    <div class=\"left-column\">\n" +
                        "        <p class=\"lead\"> Congratulations, you have created a new Quarkus application.</p>\n" +
                        "\n" +
                        "        <h2>Why do you see this?</h2>\n" +
                        "\n" +
                        "        <p>This page is served by Quarkus. The source is in\n" +
                        "            <code>src/main/resources/META-INF/resources/index.html</code>.</p>\n" +
                        "\n" +
                        "        <h2>What can I do from here?</h2>\n" +
                        "\n" +
                        "        <p>You can use the form on the right column to jump back to the logger for the reproducer.\n" +
                        "        Please change the URL in the application.properties and in this file.</p>\n" +
                        "    </div>\n" +
                        "\n" +
                        "    <div class=\"right-column\">\n" +
                        "        <div class=\"right-section\">\n" +
                        "            <h3>Application</h3>\n" +
                        "            <ul>\n" +
                        "                <li>GroupId: com.redhat.reproducer.rfe600</li>\n" +
                        "                <li>ArtifactId: rfe600-cookielogger</li>\n" +
                        "                <li>Version: 1.0.0-SNAPSHOT</li>\n" +
                        "                <li>Quarkus Version: 1.7.2.Final</li>\n" +
                        "            </ul>\n" +
                        "        </div>\n" +
                        "        <div class=\"right-section\">\n" +
                        "            <h3>Next steps</h3>\n" +
                        "\n" +
                        "            <form action=\"http://" + domain
                        + (port != "" ? port : "")
                        + "/hello\" method=\"post\">\n" +
                        "                <input name=\"data\" value=\"default value\" maxlength=\"50\" />\n" +
                        "                <button name=\"ok\" title=\"OK\" value=\"OK\" type=\"submit\" />\n" +
                        "                <button name=\"reset\" title=\"Reset\" value=\"Reset\" type=\"reset\" />\n" +
                        "            </form>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>")
                .build();
    }
}