package com.redhat.reproducer.rfe600;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.Cookie;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @ConfigProperty(name = "login.url")
    String redirectURL;

    @Test
    public void testWithCookie() {
        io.restassured.http.Cookie cookie = new Cookie.Builder("session", "abcd1234").setSameSite("Lax").build();

        given()
          .when()
                .cookie(cookie)
                .post("/hello")
          .then()
             .statusCode(200)
             .body(is("Pod: pod-not-set\n" +
                     "Cookies:\n" +
                     "- name=session, value=abcd1234 (sameSite=null, domain=null, path=null, httpOnly=false, secure=false)"));
    }


    @Test
    public void testWithoutCookie() {
        given()
            .when()
                .get("/hello")
            .then()
                .statusCode(200)
                .log();
    }
}