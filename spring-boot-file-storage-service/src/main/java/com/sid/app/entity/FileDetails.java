package com.sid.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * @author Siddhant Patni
 * Represents details of a file stored in the system.
 */
@Entity
@Table(name = "File_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDetails {


    /**
     * Unique identifier for the file.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The name of the file.
     */
    @Column(name = "file_name")
    private String name;

    /**
     * The type of the file (e.g., image/png, application/pdf).
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * The binary data of the file stored as a byte array.
     */
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    /**
     * The size of the file in bytes.
     */
    @Column(name = "file_size")
    private long fileSize;

}