package stepDefinitions;

import DTOs.PayloadDTO;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class geolocationVerificationStepDefinition {

    private static Properties properties;

    static {
        try {
            String path = "src/test/resources/config/config.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response response;

    @Given("I have valid geolocation parameters")
    public void i_have_valid_geolocation_parameters() {
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Then("the API should return a {int} status code")
    public void theAPIShouldReturnAStatusCode(int code) {
        assertEquals(code, response.getStatusCode());
    }

    @When("a valid request is sent with {string}, {string}, {string}, {string}, {string}")
    public void aValidRequestIsSentWith(String homeMobileCountryCode, String homeMobileNetworkCode, String radioType, String carrier, String considerIp) throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @And("the response should contain accurate geolocation data")
    public void theResponseShouldContainAccurateGeolocationData() {
        assertNotNull(response.jsonPath().get("location"));
        assertNotNull(response.jsonPath().get("accuracy"));
    }

    @When("a valid request is sent with only {string} and {string}")
    public void aValidRequestIsSentWithOnlyAnd(String homeMobileCountryCode, String homeMobileNetworkCode) throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @And("the response should contain geolocation data based on available information")
    public void theResponseShouldContainGeolocationDataBasedOnAvailableInformation() {
        assertNotNull(response.jsonPath().get("location"));
        assertNotNull(response.jsonPath().get("accuracy"));
    }

    @When("a valid request is sent without the considerIp field")
    public void aValidRequestIsSentWithoutTheConsiderIpField() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);
        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @When("a valid request is sent with only considerIp: true")
    public void aValidRequestIsSentWithOnlyConsiderIpTrue() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);
        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @When("a request is sent with an invalid API key")
    public void aRequestIsSentWithAnInvalidAPIKey() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);
        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key=INVALID_API_KEY");
        response.prettyPrint();
    }

    @And("the response should indicate an {string} error")
    public void theResponseShouldIndicateAnError(String error) {
        String actualErrorMessage = response.jsonPath().getString("error.message");
        Assert.assertTrue(actualErrorMessage.contains(error),"Error message does not contain the expected text.");
    }

    @When("a request is sent without an API key")
    public void aRequestIsSentWithoutAnAPIKey() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);
        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate");
        response.prettyPrint();
    }

    @When("a request is sent with an invalid homeMobileCountryCode")
    public void aRequestIsSentWithAnInvalidHomeMobileCountryCode() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode("test");
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);
        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @When("a request is sent with an invalid homeMobileNetworkCode")
    public void aRequestIsSentWithAnInvalidHomeMobileNetworkCode() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode("test");
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @When("a request is sent with an unsupported radioType")
    public void aRequestIsSentWithAnUnsupportedRadioType() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("test");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @And("the response should indicate a {string} error")
    public void theResponseShouldIndicateAError(String error) {
        String actualErrorMessage = response.jsonPath().getString("error.message");
        Assert.assertTrue(actualErrorMessage.contains(error),"Error message does not contain the expected text.");
    }

    @When("a request is sent with malformed JSON")
    public void aRequestIsSentWithMalformedJSON() throws JsonProcessingException {

        List<PayloadDTO> payloadDTOS = new ArrayList<>();
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(true);

        payloadDTOS.add(payloadDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTOS);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }

    @When("a request is sent with Consider IP as false")
    public void aRequestIsSentWithConsiderIpAsFalse() throws JsonProcessingException {
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setHomeMobileCountryCode(310);
        payloadDTO.setHomeMobileNetworkCode(410);
        payloadDTO.setRadioType("gsm");
        payloadDTO.setCarrier("Vodafone");
        payloadDTO.setConsiderIp(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(payloadDTO);

        response = RestAssured.given()
                .header("Content-Type", "application/json").log().all()
                .body(jsonBody)
                .post("/geolocate?key="+properties.getProperty("apiKey"));
        response.prettyPrint();
    }
}
