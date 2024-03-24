package com.sid.basics.app.sec11.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class FruitDto {

    private String description;
    private LocalDate expiryDate;

}