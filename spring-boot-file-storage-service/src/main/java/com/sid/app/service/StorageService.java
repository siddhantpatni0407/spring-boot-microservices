package com.sid.app.service;

import com.sid.app.entity.FileDetails;
import com.sid.app.model.FileData;
import com.sid.app.repository.StorageRepository;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Siddhant Patni
 */

@Service
@Slf4j
public class StorageService {


    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {
        FileDetails fileDetails = repository
                .save(FileDetails
                        .builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .fileData(ApplicationUtils.compressImage(file.getBytes()))
                        .build());

        if (fileDetails != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<FileDetails> dbImageData = repository.findByName(fileName);
        byte[] images = ApplicationUtils.decompressImage(dbImageData.get().getFileData());
        return images;
    }

    public List<FileData> getAllFileData() {
        List<FileDetails> repositoryData = repository.findAll();
        if (log.isInfoEnabled()) {
            log.info("getAllFileData() : repositoryData -> {}", ApplicationUtils.getJSONString(repositoryData));
        }
        List<FileData> fileData = repositoryData
                .stream()
                .map(fileDetails -> {
                    FileData fileData1 = new FileData();
                    fileData1.setId(fileDetails.getId());
                    fileData1.setName(fileDetails.getName());
                    fileData1.setType(fileDetails.getType());
                    //fileData1.setFileData(Arrays.toString(fileDetails.getImageData()));
                    return fileData1;
                }).collect(Collectors.toList());
        return fileData;
    }

}