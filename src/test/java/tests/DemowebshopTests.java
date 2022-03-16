package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemowebshopTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.filters(withCustomTemplates());
    }

    @Test
    @Tag("api")
    void addToCartWithCookieTest() {
        String cardCount =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookie("Nop.customer=88f590c6-59e9-4a55-b243-7395b35f0ce2;")
                        .when()
                        .get("http://demowebshop.tricentis.com/cart")
                        .then().extract().body().asString().split("cart-qty\">")[1]
                        .split("</")[0].replaceAll("[^\\d ]", "");

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=88f590c6-59e9-4a55-b243-7395b35f0ce2;")
                .body("product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml",
                        is(String.format("(%d)", Integer.parseInt(cardCount) + 1)));
    }
}
