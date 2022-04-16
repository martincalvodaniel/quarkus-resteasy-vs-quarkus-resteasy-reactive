package com.dmartinc;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ExampleResourceTest {

    @Test
    void noUrlEncodedQueryParam() {
        given()
                .when().get("/hello?queryParam=arg;123")
                .then()
                .statusCode(200)
                .body(is("Hello RESTEasy CLASSIC. This was your received queryParam: arg;123"));
    }
}
