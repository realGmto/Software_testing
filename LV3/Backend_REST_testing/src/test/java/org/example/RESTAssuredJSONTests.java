package org.example;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RESTAssuredJSONTests {
    final static String ROOT_URI = "http://localhost:7000/employees";

    @Test
    public void simple_get_test() {
        Response response = get(ROOT_URI + "/list");
        System.out.println(response.asString());
        response.then().body("id", hasItems(1, 2));
        response.then().body("name", hasItems("Pankaj"));
    }
    @Test
    public void post_test() {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Lisa\",\"salary\": \"2000\"}")
                .when()
                .post(ROOT_URI + "/create");
        System.out.println("POST Response\n" + response.asString());
// tests
        response.then().body("id", Matchers.any(Integer.class));
        response.then().body("name", Matchers.is("Lisa"));
    }
    @Test
    public void put_test() {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Lisa Tamaki\",\"salary\": \"45000\"}")
                .when()
                .put(ROOT_URI + "/update/3");
        System.out.println("PUT Response\n" + response.asString());
        response.then().body("id", Matchers.is(3));
        response.then().body("name", Matchers.is("Lisa Tamaki"));
        response.then().body("salary", Matchers.is("45000"));

    }
}
