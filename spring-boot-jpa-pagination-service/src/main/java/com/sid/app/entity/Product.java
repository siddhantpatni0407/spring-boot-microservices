package com.sid.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Siddhant Patni
 */
@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_price")
    private long price;

    public Product(String name, int quantity, long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

}