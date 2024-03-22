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
public class Customer {

    private Integer id;
    private String name;
    private Integer age;
    private String city;

}