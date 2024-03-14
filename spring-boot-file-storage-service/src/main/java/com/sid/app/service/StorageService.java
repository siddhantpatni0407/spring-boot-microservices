package com.sid.app.service;

import com.sid.app.constant.AppConstants;
import com.sid.app.entity.FileDetails;
import com.sid.app.exception.FileNameNotCorrectError;
import com.sid.app.exception.ResourceNotFoundException;
import com.sid.app.model.FileData;
import com.sid.app.model.Response;
import com.sid.app.repository.StorageRepository;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Siddhant Patni
 * Service class for handling file storage operations.
 */
@Service
@Slf4j
@SuppressWarnings("PMD")
public class StorageService {

    @Autowired
    private StorageRepository repository;

    /**
     * Uploads a file to the storage service.
     *
     * @param file The file to be uploaded.
     * @return A response indicating the upload status.
     * @throws Exception If an error occurs during the upload process.
     */
    public Response uploadFile(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new FileNameNotCorrectError(fileName);
        }

        FileDetails fileDetails = repository
                .save(FileDetails
                        .builder()
                        .name(ApplicationUtils.removeSpecialCharacters(file.getOriginalFilename()))
                        .fileType(file.getContentType())
                        .fileData(ApplicationUtils.compressFile(file.getBytes()))
                        .fileSize(file.getSize())
                        .build());

        Response response = Response.builder().build();
        if (!ObjectUtils.isEmpty(fileDetails)) {
            response.setStatus("File Uploaded Successfully. File Name : " + file.getOriginalFilename());
        } else {
            response.setStatus("Unable to save file");
        }
        return response;
    }

    /**
     * Downloads a file from the storage service.
     *
     * @param id The ID of the file to download.
     * @return The file details.
     */
    public FileDetails downloadFile(long id) {
        FileDetails dbFileData = repository.findById(id);
        if (!ObjectUtils.isEmpty(dbFileData)) {
            dbFileData.setFileData(ApplicationUtils.decompressFile(dbFileData.getFileData()));
        } else {
            throw new ResourceNotFoundException(id);
        }

        //byte[] files = ApplicationUtils.decompressFile(dbFileData.get().getFileData());
        return dbFileData;
    }

    /**
     * Retrieves details of all files stored in the service.
     *
     * @return A list of file data.
     */
    public List<FileData> getAllFileDetails() {
        List<FileDetails> repositoryData = repository.findAll();
        if (log.isInfoEnabled()) {
            log.info("getAllFileDetails() : repositoryData -> {}", ApplicationUtils.getJSONString(repositoryData));
        }

        List<FileData> fileData = repositoryData
                .stream()
                .map(fileDetails -> {
                    FileData fileData1 = new FileData();
                    fileData1.setId(fileDetails.getId());
                    fileData1.setName(fileDetails.getName());
                    fileData1.setType(fileDetails.getFileType());
                    fileData1.setFileDownloadURL(ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.FILE_DOWNLOAD_ENDPOINT).queryParam("id", fileDetails.getId()).toUriString());
                    fileData1.setFileSize(ApplicationUtils.getFileSize(fileDetails.getFileSize()));
                    return fileData1;
                }).collect(Collectors.toList());
        return fileData;
    }

    /**
     * Deletes a file from the storage service.
     *
     * @param id The ID of the file to delete.
     * @return A response indicating the deletion status.
     */
    public Response deleteFile(Long id) {
        Response response = Response.builder().build();
        if (repository.existsById(id)) {
            repository.deleteById(id);
            response.setStatus("File with id " + id + " has been deleted successfully.");
            if (log.isInfoEnabled()) {
                log.info("deleteFile() : File with id : {} has been deleted successfully", id);
            }
        } else {
            response.setStatus("Could not found the file with id " + id);
            throw new ResourceNotFoundException(id);
        }

        return response;
    }

}