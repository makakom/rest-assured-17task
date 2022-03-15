package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresTest {

    @Test
    void testCreateUser() {
        User user = new User("morpheus", "leader");
        given()
                .contentType(JSON)
                .body(user.createUserRequest())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is(user.getName()))
                .body("job", is(user.getJob()));
    }

    @Test
    void testUpdateUser() {
        User user = new User("morpheus", "zion resident");
        given()
                .contentType(JSON)
                .body(user.createUserRequest())
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is(user.getName()))
                .body("job", is(user.getJob()));
    }

    @Test
    void testDeleteUser() {
        given()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void testGetSingleUser() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.id", is(2));
    }

    @Test
    void testGetListUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.email[3]", is("byron.fields@reqres.in"));
    }
}
