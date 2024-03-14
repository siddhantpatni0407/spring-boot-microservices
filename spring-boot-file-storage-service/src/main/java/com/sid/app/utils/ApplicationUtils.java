package com.sid.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author Siddhant Patni
 * Utility class for common application functionalities.
 */
@Component
@Slf4j
@SuppressWarnings("PMD")
public class ApplicationUtils {

    /**
     * Converts an object to its JSON string representation.
     *
     * @param <T>    the type parameter
     * @param object the object to convert
     * @return the JSON string representation of the object
     */
    public static <T> String getJSONString(T object) {
        if (object != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Error occurred [{}] while converting to string [{}]", e.getMessage(), object);
                }
            }
        }
        return "";
    }

    /**
     * Converts a file size in bytes to a human-readable format.
     *
     * @param fileSizeBytes the file size in bytes
     * @return the human-readable file size string
     */
    public static String getFileSize(long fileSizeBytes) {

        // Convert the size to KB or MB
        double fileSizeKB = fileSizeBytes / 1024.0; // Convert bytes to KB
        double fileSizeMB = fileSizeKB / 1024.0;   // Convert KB to MB

        // Format the size as a string with appropriate unit
        String fileSizeStr;
        if (fileSizeMB >= 1.0) {
            fileSizeStr = String.format("%.2f MB", fileSizeMB);
        } else {
            fileSizeStr = String.format("%.2f KB", fileSizeKB);
        }

        return fileSizeStr;
    }

    /**
     * Removes special characters from a file name.
     *
     * @param fileName the file name to sanitize
     * @return the sanitized file name
     */
    public static String removeSpecialCharacters(String fileName) {
        // Regular expression to match special characters
        String regex = "[^a-zA-Z0-9\\.\\-_]"; // Allow alphabets, digits, period, hyphen, and underscore
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher object
        Matcher matcher = pattern.matcher(fileName);
        // Replace special characters with an empty string
        return matcher.replaceAll("");
    }

    /**
     * Compresses a byte array using the Deflater algorithm.
     *
     * @param data the byte array to compress
     * @return the compressed byte array
     */
    public static byte[] compressFile(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    /**
     * Decompresses a byte array using the Inflater algorithm.
     *
     * @param data the byte array to decompress
     * @return the decompressed byte array
     */
    public static byte[] decompressFile(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

}