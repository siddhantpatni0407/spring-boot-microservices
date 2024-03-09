package com.sid.app.service;

import com.sid.app.entity.Image;
import com.sid.app.repository.StorageRepository;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Siddhant Patni
 */

@Service
@Slf4j
public class StorageService {


    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {
        Image image = repository
                .save(Image
                        .builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ApplicationUtils.compressImage(file.getBytes()))
                        .build());

        if (image != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = repository.findByName(fileName);
        byte[] images = ApplicationUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}