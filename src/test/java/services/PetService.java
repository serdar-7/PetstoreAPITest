package services;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import model.*;
import utils.PropertiesFile;


import java.util.List;
import java.util.Map;

public class PetService extends BaseService {

    private static final String PATH = PropertiesFile.getProperty("path");
    private static final String FIND_BY_STATUS = PropertiesFile.getProperty("find_by_status");


    public PetService() {
        super();
    }


    /**
     * Add new pet to the Pet Store.
     *
     * @param payload request body
     * @return Response
     */
    public Response addPetToStore(String payload) {
        return post(payload, null, PATH);
    }

    /**
     * Get pet from Pet Store with ID.
     *
     * @param pet to be fetched
     * @return Response
     */
    public Response getPetwId(Pet pet) {
        return get(null, PATH + pet.getId());
    }

    /**
     * Get pet from Pet Store with Status.
     *
     * @param parameters to be fetched
     * @return Response
     */
    public Response getPetwStatus(Map<String, String> parameters) {
        return get(parameters, FIND_BY_STATUS);
    }

    /**
     * Update the existing pet in the Pet Store.
     *
     * @param payload request body
     * @return Response
     */
    public Response updatePet(String payload) {
        return put(payload, null, PATH);
    }

    /**
     * Remove pet from Pet Store.
     *
     * @param pet pet to be removed
     * @return Response
     */
    public Response deletePet(Pet pet) {
        return delete(null, PATH + pet.getId());
    }


    /**
     * Compare two Pet objects' attributes
     *
     * @param response returned response
     * @param body     Pet object in the request payload
     */
    public void comparePetObjects(Pet response, Pet body) {
        Assertions.assertEquals(response.getId(), body.getId());
        Assertions.assertEquals(response.getName(), body.getName());
        Assertions.assertEquals(response.getCategory().getId(), body.getCategory().getId());
        Assertions.assertEquals(response.getCategory().getName(), body.getCategory().getName());
        Assertions.assertEquals(response.getPhotoUrls(), body.getPhotoUrls());
        if (response.getTags() != null) {
            for (int i = 0; i < response.getTags().size(); i++) {
                Assertions.assertEquals(response.getTags().get(i).getId(), body.getTags().get(i).getId());
                Assertions.assertEquals(response.getTags().get(i).getName(), body.getTags().get(i).getName());
            }
        }
        Assertions.assertEquals(response.getStatus(), body.getStatus());
    }

    /**
     * checks list of Pet objects status code if it is matches with the given status value
     *
     * @param response  List<Pet>
     * @param attribute status to be checked
     **/
    public void validatePetStatus(List<Pet> response, String attribute) {
        response.forEach(pet -> Assert.assertEquals(attribute + " of the pet with the " + pet.getId() + " id does not match", attribute, pet.getStatus()));
    }
}
