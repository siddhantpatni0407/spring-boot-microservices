package com.sid.app.service;

import com.sid.app.entity.Product;
import com.sid.app.exception.CustomException;
import com.sid.app.repository.ProductRepository;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
@SuppressWarnings("PMD")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Mono<List<Product>> addProductsInDB() {
        List<Product> products = IntStream
                .rangeClosed(1, 200)
                .mapToObj(i -> new Product("product" + i, new Random().nextInt(100), new Random().nextInt(50000)))
                .collect(Collectors.toList());
        List<Product> productsList = productRepository.saveAll(products);
        if (log.isInfoEnabled()) {
            log.info("addProductsInDB() : Add Products API. productsList -> {}", ApplicationUtils.getJSONString(productsList));
        }
        return Mono.just(productsList);
    }

    public List<Product> getAllProducts() {
        List<Product> productList;
        try {
            productList = productRepository.findAll();
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage());
        }
        return productList;
    }

    public List<Product> findProductsWithSorting(String field) {
        List<Product> productList;
        try {
            productList = productRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage());
        }
        return productList;
    }

    public Page<Product> findProductsWithPagination(int offset, int pageSize) {
        Page<Product> products;
        try {
            products = productRepository.findAll(PageRequest.of(offset, pageSize));
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage());
        }
        return products;
    }

    public Page<Product> findProductsWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Product> products;
        try {
            products = productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage());
        }
        return products;
    }

}