package com.sid.app.model;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

/**
 * @author Siddhant Patni
 */
@SuppressWarnings("PMD")
public class FileDataTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange
        FileData fileData = new FileData();

        // Assert
        assertNotNull(fileData);
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        FileData fileData = new FileData(1L, "test.txt", "text/plain", "http://example.com", "100 KB");

        // Assert
        assertNotNull(fileData);
        assertEquals(1L, fileData.getId());
        assertEquals("test.txt", fileData.getName());
        assertEquals("text/plain", fileData.getType());
        assertEquals("http://example.com", fileData.getFileDownloadURL());
        assertEquals("100 KB", fileData.getFileSize());
    }

    @Test
    public void testSetterGetterMethods() {
        // Arrange
        FileData fileData = new FileData();

        // Act
        fileData.setId(1L);
        fileData.setName("test.txt");
        fileData.setType("text/plain");
        fileData.setFileDownloadURL("http://example.com");
        fileData.setFileSize("100 KB");

        // Assert
        assertEquals(1L, fileData.getId());
        assertEquals("test.txt", fileData.getName());
        assertEquals("text/plain", fileData.getType());
        assertEquals("http://example.com", fileData.getFileDownloadURL());
        assertEquals("100 KB", fileData.getFileSize());
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