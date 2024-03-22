package com.sid.basics.app.sec05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Account {

    private UUID id;
    private Integer amount;
    private String accountType;

}