package com.sid.app.controller;

import com.sid.app.entity.FileDetails;
import com.sid.app.model.FileData;
import com.sid.app.model.Response;
import com.sid.app.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StorageControllerTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private StorageController storageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadFile() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        Response expectedResponse = new Response("Success");
        when(storageService.uploadFile(file)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Response> responseEntity = storageController.uploadFile(file);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testDownloadFile() {
        // Arrange
        long id = 1L;
        FileDetails fileDetails = new FileDetails();
        fileDetails.setName("test.txt");
        fileDetails.setFileType("text/plain");
        byte[] fileData = "Sample file data".getBytes();
        fileDetails.setFileData(fileData);
        ResponseEntity<ByteArrayResource> expectedResponse = ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileDetails.getName() + "\"")
                .contentType(MediaType.parseMediaType(fileDetails.getFileType()))
                .body(new ByteArrayResource(fileData));
        when(storageService.downloadFile(id)).thenReturn(fileDetails);

        // Act
        ResponseEntity<?> responseEntity = storageController.downloadFile(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ByteArrayResource);
        ByteArrayResource responseBody = (ByteArrayResource) responseEntity.getBody();
        assertArrayEquals(fileData, responseBody.getByteArray());
        assertEquals(expectedResponse.getHeaders().getContentDisposition(), responseEntity.getHeaders().getContentDisposition());
        assertEquals(expectedResponse.getHeaders().getContentType(), responseEntity.getHeaders().getContentType());
    }


    @Test
    public void testGetAllFileDetails() {
        // Arrange
        List<FileData> expectedFileDataList = Arrays.asList(
                new FileData(1L, "file1.txt", "text/plain", "http://localhost:8080/api/v1/file-storage-service/file/download?id=1", "100 KB"),
                new FileData(2L, "file2.txt", "text/plain", "http://localhost:8080/api/v1/file-storage-service/file/download?id=2", "150 KB")
        );
        when(storageService.getAllFileDetails()).thenReturn(expectedFileDataList);

        // Act
        ResponseEntity<List<FileData>> responseEntity = storageController.getAllFileDetails();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedFileDataList, responseEntity.getBody());
    }

    @Test
    public void testDeleteFile() {
        // Arrange
        long id = 1L;
        Response expectedResponse = new Response("File deleted successfully");
        when(storageService.deleteFile(id)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Response> responseEntity = storageController.deleteFile(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

}