package com.sid.sb.jwt.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "UserRole")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = true, updatable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = true, updatable = true)
    private Role role;

}