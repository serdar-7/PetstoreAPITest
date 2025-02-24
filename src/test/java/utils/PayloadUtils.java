package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PayloadUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Assign the Pet object's attributes with the data from cucumber datatable and serialize for the request payload
     *
     * @param pet           Pet Object to be Serialized
     * @param dynamicFields Related objects to create the payload
     * @return payload
     */
    public static String createDynamicPayload(Pet pet, Map<String, String> dynamicFields) {

        try {
            if (dynamicFields != null) {
                if (dynamicFields.get("petId") != null) {
                    pet.setId(Long.valueOf(dynamicFields.get("petId")));
                }
                if (dynamicFields.get("petName") != null) {
                    pet.setName(dynamicFields.get("petName"));
                }
                pet.setStatus(dynamicFields.get("status"));
                List<String> photosUrls = Arrays.stream(dynamicFields.get("photoUrls").split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                pet.setPhotoUrls(photosUrls);
                if (dynamicFields.get("categoryId") != null && dynamicFields.get("categoryName") != null) {
                    pet.setCategory(new Category(Long.parseLong(dynamicFields.get("categoryId")), dynamicFields.get("categoryName")));
                }
                if (dynamicFields.get("tags") != null) {
                    List<Tag> tags = Arrays.stream(dynamicFields.get("tags").split(","))
                            .map(tag -> new Tag(System.currentTimeMillis(), tag.trim()))
                            .collect(Collectors.toList());
                    pet.setTags(tags);
                }
            }
            System.out.println(objectMapper.writeValueAsString(pet));
            return objectMapper.writeValueAsString(pet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create dynamic payload", e);
        }
    }


    /**
     * Checks if response body contains json array or not and deserialize as a Pet or Pet Array
     *
     * @param json Response Body
     * @return Pet object
     */
    public static <T> Object deserializeJson(String json, Class<T> cls) {
        try {
            // Check if JSON is an array or an object
            if (json.trim().startsWith("[")) {
                // JSON is an array -> Deserialize into a List<T>
                return objectMapper.readValue(json, new TypeReference<List<Pet>>() {
                });
            } else {
                // JSON is a single object -> Deserialize into a single T object
                return objectMapper.readValue(json, cls);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }
}
