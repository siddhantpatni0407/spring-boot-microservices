package com.sid.app.repository;

import com.sid.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siddhant Patni
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


}