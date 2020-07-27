import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ApiTest {

    ApiTestHelper apiTestHelper = new ApiTestHelper();


    String apiKey = "852159f0";
    String searchWord = "Harry Potter";
    String movieTitle = "Harry Potter and the Sorcerer's Stone";


    @Test
    public void ApiTest(){
        //  finding imdbID from response of the getIdFromMovie()
        String id = apiTestHelper.getIdFromMovie(apiKey, searchWord, movieTitle);

        // testing  response body valitation (title,year,released) of the movie
        apiTestHelper.searchByID(apiKey, id);
    }


}




