import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class ApiTestHelper {

    String baseURI = "http://www.omdbapi.com/";

    public String getIdFromMovie(String apiKey, String searchWord, String movieTitle) {
        RestAssured.baseURI = baseURI;
        String id = null;
        try {

            Response response = getResponseFromEndPoint(apiKey,searchWord);
            JsonPath path = response.jsonPath();
            List<MovieDTO> data = path.getList("Search", MovieDTO.class);

            for (MovieDTO singleObject : data) {
                if (singleObject.getTitle().equals(movieTitle)) {
                    id = singleObject.getImdbID();
                    System.out.println("imdbID: " + id + "\n");
                    break;
                }
            }
            return id;
        }catch (Exception ex){
            System.out.println("Error! " + ex.getMessage());
            return null;
        }
    }

    public void searchByID(String apiKey, String id) {
        try {
            given()
                    .param("apikey", apiKey)
                    .param("i",id)
                    .when()
                    .get()
                    .then()
                    .log()
                    .all()
                    .statusCode(200)
                    .body("Title", not(emptyOrNullString()))
                    .body("Year", not(emptyOrNullString()))
                   .body("Released", not(emptyOrNullString()));
        }catch (Exception ex){
            System.out.println("Error! "+ex.getMessage());
        }
    }

    private Response getResponseFromEndPoint(String apiKey, String searchWord) {
        try {
            return given()
                    .param("apikey", apiKey)
                    .param("s", searchWord)
                    .when()
                    .get()
                    .then()
                    .contentType(ContentType.JSON)
                    .statusCode(200)
                    .and()
                    .extract()
                    .response();
        }catch (Exception ex){
            System.out.println("Error! "+ex.getMessage());
            return null;
        }
    }
}