package com.sid.app.repository;

import com.sid.app.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Siddhant Patni
 */
@Repository
public interface StorageRepository extends JpaRepository<FileDetails, Long> {

    FileDetails findByName(String fileName);


}