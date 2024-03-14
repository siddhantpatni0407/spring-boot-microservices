package com.sid.app.service;

import com.sid.app.entity.FileDetails;
import com.sid.app.model.FileData;
import com.sid.app.model.Response;
import com.sid.app.repository.StorageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Siddhant Patni
 */
@SpringBootTest
@SuppressWarnings("PMD")
public class StorageServiceTest {

    @Mock
    private StorageRepository repository;

    @InjectMocks
    private StorageService storageService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadFile() throws Exception {
        // Arrange
        String fileName = "test.txt";
        byte[] fileContent = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "text/plain", fileContent);

        // Mock repository behavior
        when(repository.save(any())).thenReturn(new FileDetails());

        // Act
        Response response = storageService.uploadFile(multipartFile);

        // Assert
        assertEquals("File Uploaded Successfully. File Name : " + fileName, response.getStatus());
    }

    /*@Test
    public void testDownloadFile() {
        // Arrange
        long fileId = 1L;
        FileDetails fileDetails = new FileDetails(1L, "file1.txt", "text/plain", new byte[]{}, 100);
        fileDetails.setId(fileId);

        // Mock repository behavior
        when(repository.findById(fileId)).thenReturn(fileDetails);

        // Act
        FileDetails downloadedFile = storageService.downloadFile(fileId);

        // Assert
        assertEquals(fileId, downloadedFile.getId());
    }*/

    @Test
    public void testGetAllFileDetails() {
        // Arrange
        List<FileDetails> expectedFileDetailsList = Arrays.asList(
                new FileDetails(1L, "file1.txt", "text/plain", new byte[]{}, 100),
                new FileDetails(2L, "file2.txt", "text/plain", new byte[]{}, 150)
        );

        // Mock repository behavior
        when(repository.findAll()).thenReturn(expectedFileDetailsList);

        // Act
        List<FileData> fileDataList = storageService.getAllFileDetails();

        // Assert
        assertEquals(expectedFileDetailsList.size(), fileDataList.size());
        // Add more assertions based on your application logic
    }

    @Test
    public void testDeleteFile() {
        // Arrange
        long fileId = 1L;

        // Mock repository behavior
        when(repository.existsById(fileId)).thenReturn(true);

        // Act
        Response response = storageService.deleteFile(fileId);

        // Assert
        assertEquals("File with id " + fileId + " has been deleted successfully.", response.getStatus());
        // Add more assertions based on your application logic
    }

}