package com.sid.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationUtilsTest {


    @Test
    public void testGetFileSize() {
        // Arrange
        long fileSizeBytes = 1024 * 1024; // 1 MB
        String expectedSize = "1.00 MB";

        // Act
        String size = ApplicationUtils.getFileSize(fileSizeBytes);

        // Assert
        assertEquals(expectedSize, size);
    }

    @Test
    public void testRemoveSpecialCharacters() {
        // Arrange
        String fileName = "file_name_123.txt";
        String expectedFileName = "file_name_123.txt";

        // Act
        String sanitizedFileName = ApplicationUtils.removeSpecialCharacters(fileName);

        // Assert
        assertEquals(expectedFileName, sanitizedFileName);
    }

    @Test
    public void testCompressFile() {
        // Arrange
        byte[] data = "sample data".getBytes();

        // Act
        byte[] compressedData = ApplicationUtils.compressFile(data);

        // Assert
        assertNotNull(compressedData);
        assertNotEquals(data, compressedData);
    }

    @Test
    public void testDecompressFile() {
        // Arrange
        byte[] data = "sample data".getBytes();
        byte[] compressedData = ApplicationUtils.compressFile(data);

        // Act
        byte[] decompressedData = ApplicationUtils.decompressFile(compressedData);

        // Assert
        assertNotNull(decompressedData);
        assertArrayEquals(data, decompressedData);
    }

}