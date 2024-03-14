package com.sid.app.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Siddhant Patni
 */
@SuppressWarnings("PMD")
public class ResponseTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange
        Response response = new Response();

        // Assert
        assertNotNull(response);
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        Response response = new Response("Success", "File not found");

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("File not found", response.getErrorMessage());
    }

    @Test
    public void testBuilder() {
        // Arrange
        Response response = Response.builder()
                .status("Success")
                .errorMessage("File not found")
                .build();

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("File not found", response.getErrorMessage());
    }

    @Test
    public void testSetterGetterMethods() {
        // Arrange
        Response response = new Response();
        response.setStatus("Success");
        response.setErrorMessage("File not found");

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("File not found", response.getErrorMessage());
    }

    @Test
    public void testJsonSerialization() {
        // Arrange
        Response response = new Response("Success", "File not found");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Act
        String json;
        try {
            json = mapper.writeValueAsString(response);
            System.out.println("Generated JSON: " + json);
        } catch (Exception e) {
            fail("Exception occurred while serializing to JSON: " + e.getMessage());
            return;
        }

        // Assert
        assertNotNull(json);

        // Parse the JSON string to verify the fields
        try {
            Response parsedResponse = mapper.readValue(json, Response.class);
            assertEquals("Success", parsedResponse.getStatus());
            assertEquals("File not found", parsedResponse.getErrorMessage());
        } catch (Exception e) {
            fail("Exception occurred while parsing JSON: " + e.getMessage());
        }
    }
}
