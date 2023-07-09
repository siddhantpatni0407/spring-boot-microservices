package com.sid.app.entity;

import com.sid.app.utils.Status;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Siddhant Patni
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "email_transactions")
public class EmailTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correlation_id")
    private String correlationID;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "to_address")
    private String to;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "failure_description")
    private String failureDescription;

}