import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class JIRAApiTest {

    @Test
    public void getIssue() {

        Response getIssue =
                given().
                        auth().preemptive().basic("Ekaterina_Voronkova", "Ekaterina_Voronkova").
                        when().
                        get("http://jira.hillel.it/rest/api/2/issue/QAAUT8-1282").
                        then().
                        extract().response();

        assertEquals(200, getIssue.statusCode());
        assertEquals("QAAUT8-1282", getIssue.path("key"));
    }

    @Test
    public void createIssue() {
        String issueJSON = "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"id\": \"11400\"\n" +
                "        },\n" +
                "        \"summary\": \"Test API\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        },\n" +
                "        \"assignee\": {\n" +
                "            \"name\": \"Ekaterina_Voronkova\"\n" +
                "        },\n" +
                "        \"reporter\": {\n" +
                "            \"name\": \"Ekaterina_Voronkova\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Response createIssue =
                given().
                        auth().preemptive().basic("Ekaterina_Voronkova", "Ekaterina_Voronkova").
                        header("Content-Type", "application/json").
                        body(issueJSON).
                        when().
                        post("https://jira.hillel.it/rest/api/2/issue").
                        then().
                        extract().response();

        assertEquals(201, createIssue.statusCode());
        String responseBodyCreate = createIssue.then().extract().asString();
        System.out.printf("\nRESPONSE: " + responseBodyCreate);
    }

    @Test
    public void deleteIssue() {
        Response deleteIssue =
                given().
                        auth().preemptive().basic("Ekaterina_Voronkova", "Ekaterina_Voronkova").
                        when().
                        delete("https://jira.hillel.it/rest/api/2/issue/67487").
                        then().
                        extract().response();

        assertEquals(204, deleteIssue.statusCode());
        System.out.println("\nStatus code:" + deleteIssue.statusCode());
    }

    @Test
    public void getExistingIssue() {

        Response getExistingIssue =
                given().
                        auth().preemptive().basic("Ekaterina_Voronkova", "Ekaterina_Voronkova").
                        when().
                        get("http://jira.hillel.it/rest/api/2/issue/67487").
                        then().
                        extract().response();

      assertEquals(getExistingIssue.statusCode(),404);
      System.out.println("Status code:" + getExistingIssue.statusCode());
      String responseBodyGet = getExistingIssue.then().extract().asString();
      System.out.printf("\nRESPONSE: " + responseBodyGet);
    }


}

