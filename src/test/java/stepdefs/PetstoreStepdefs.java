package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import model.ApiResponse;
import model.Pet;
import services.PetService;

import java.util.List;
import java.util.Map;

import static utils.PayloadUtils.createDynamicPayload;
import static utils.PayloadUtils.deserializeJson;

public class PetstoreStepdefs {
    Pet lastPet = new Pet();
    PetService petService = new PetService();

    private Response response = null;

    String status;

    public PetstoreStepdefs() {
    }

    @When("Create a new pet to the store")
    public void createANewPetToTheStore(DataTable dataTable) {
        Map<String, String> datatable = dataTable.asMaps().get(0);
        response = petService.addPetToStore(createDynamicPayload(lastPet, datatable));
        System.out.println("Post Response " + response.getBody().asString());
    }

    @Then("Response body should match with the request payload")
    public void responseBodyShouldMatchWithTheRequestPayload() {
        petService.comparePetObjects((Pet) deserializeJson(response.getBody().asString(), Pet.class), lastPet);
    }

    @Then("Response code returns <{int}>")
    public void responseCodeReturns(int expectedStatusCode) {
        petService.verifyResponseStatusValue(response, expectedStatusCode);
    }

    @Then("Retrieve the created pet using the GET endpoint")
    public void retrieveTheCreatedPetFromTheStore() {
        response = petService.getPetwId(lastPet);
    }

    @Then("Verify created Pet returns from Get endpoint")
    public void verifyReturnedPetMatchesWithTheCreatedPet() {
        petService.comparePetObjects((Pet) deserializeJson(response.getBody().asString(), Pet.class), lastPet);
    }

    @When("Update the created pet in the store")
    public void updateTheCreatedPetInTheStore(DataTable dataTable) {
        Map<String, String> datatable = dataTable.asMaps().get(0);
        response = petService.updatePet(createDynamicPayload(lastPet, datatable));
        System.out.println("Update Response " + response.getBody().asString());
    }

    @And("Delete the created pet")
    public void deleteTheCreatedPet() {
        response = petService.deletePet(lastPet);
        System.out.println(response.getBody().asString());

    }

    @When("Retrieve pets with status from the Petstore")
    public void retrievePetsWithStatusFromThePetstore(DataTable dataTable) {
        Map<String, String> datatable = dataTable.asMaps().get(0);
        response = petService.getPetwStatus(datatable);
        status = datatable.get("status");
        System.out.println(response.getBody().asString());

    }

    @Then("Response contains pets with requested status")
    public void responseContainsPetsWithRequestedStatus() {
        List<Pet> pets = (List<Pet>) deserializeJson(response.getBody().asString(), Pet.class);
        petService.validatePetStatus(pets, status);
    }


    @Then("Response message attribute matches with the Pet Id")
    public void responseMessageMatchesWithThePetId() {
        petService.validatApiResponseMessage((ApiResponse) deserializeJson(response.getBody().asString(), ApiResponse.class), String.valueOf(lastPet.getId()));
    }

    @Then("Validate the response schema")
    public void validateTheResponseSchema() {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/findByStatusSchema.json"));
    }

    @Given("Delete not found pet id <{int}>")
    public void deletePetWithId(int petId) {
        Pet pet = new Pet();
        pet.setId((long) petId);
        response = petService.deletePet(pet);

    }

    @When("Get not found pet id <{int}>")
    public void getNotFoundPetId(int petId) {
        Pet pet = new Pet();
        pet.setId((long) petId);
        response = petService.getPetwId(pet);
    }


    @When("Send post request with invalid body {string}")
    public void sendPostRequestWithInvalidBody(String body) {
        response = petService.addPetToStore(body);
    }

    @When("Send put request with invalid body {string}")
    public void sendPutRequestWithInvalidBody(String body) {
        response = petService.addPetToStore(body);
    }


}
