package com.sid.basics.app.sec05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Address {

    private String street;
    private String city;

}