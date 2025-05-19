package restAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class PetStoreApiTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final long PET_ID = 999999;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1)
    public void testPost_CreatePet() {
        String requestBody = """
            {
              "id": %d,
              "name": "TestPet",
              "status": "available"
            }
            """.formatted(PET_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        response.then().log().body();

        response.then().statusCode(200);
        assertEquals(response.jsonPath().getLong("id"), PET_ID);
        assertEquals(response.jsonPath().getString("name"), "TestPet");
    }

    @Test(priority = 2)
    public void testGet_FetchPetById() {
        Response response = given()
                .get("/pet/"+PET_ID);

        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void testPut_UpdatePet() {
        String updatedRequestBody = """
            {
              "id": %d,
              "name": "UpdatedPet",
              "status": "sold"
            }
            """.formatted(PET_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .put("/pet");

        response.then().statusCode(200);
        assertEquals(response.jsonPath().getString("name"), "UpdatedPet");
        assertEquals(response.jsonPath().getString("status"), "sold");
    }

    @Test(priority = 4)
    public void testDelete_RemovePet() {
        Response response = given()
                .delete("/pet/"+ PET_ID);

        response.then().statusCode(200);
    }

}
