package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.entity.Product;
import com.sid.app.model.APIResponse;
import com.sid.app.service.ProductService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = AppConstants.PRODUCTS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<List<Product>> addProducts() {
        if (log.isInfoEnabled()) {
            log.info("addProducts() : Add Products - START");
        }
        return productService.addProductsInDB()
                .flatMap(products -> {
                    if (log.isInfoEnabled()) {
                        log.info("addProducts() : Add Employee API. Response -> {}",
                                ApplicationUtils.getJSONString(products));
                        log.info("addProducts() : Add Products - END");
                    }
                    return Mono.just(products);
                });
    }

    @GetMapping(value = AppConstants.PRODUCTS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private APIResponse<List<Product>> getAllProducts() {
        if (log.isInfoEnabled()) {
            log.info("getAllProducts() : Get All Products - START");
        }
        APIResponse<List<Product>> apiResponse = new APIResponse<>();
        List<Product> allProducts = productService.getAllProducts();
        apiResponse.setRecordCount(allProducts.size());
        apiResponse.setResponse(allProducts);
        if (log.isInfoEnabled()) {
            log.info("getAllProducts() : Get All Products API. Response -> {}",
                    ApplicationUtils.getJSONString(apiResponse));
            log.info("getAllProducts() : Get All Products - END");
        }
        return apiResponse;
    }

    @GetMapping(value = AppConstants.PRODUCTS_WITH_SORT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private APIResponse<List<Product>> getProductsWithSort(@RequestParam(value = "field") String field) {
        if (log.isInfoEnabled()) {
            log.info("getProductsWithSort() : Get All Products With Sorting - START");
        }
        APIResponse<List<Product>> apiResponse = new APIResponse<>();
        List<Product> allProducts = productService.findProductsWithSorting(field);
        apiResponse.setRecordCount(allProducts.size());
        apiResponse.setResponse(allProducts);
        if (log.isInfoEnabled()) {
            log.info("getProductsWithSort() : Get All Products With Sorting API. Request Param = field -> {} andResponse -> {}",
                    field, ApplicationUtils.getJSONString(apiResponse));
            log.info("getProductsWithSort() : Get All Products With Sorting - END");
        }
        return apiResponse;
    }

    @GetMapping(value = AppConstants.PRODUCTS_PAGINATION_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private APIResponse<Page<Product>> getProductsWithPagination(@RequestParam(value = "offset") int offset,
                                                                 @RequestParam(value = "pageSize") int pageSize) {
        if (log.isInfoEnabled()) {
            log.info("getProductsWithPagination() : Get All Products With Pagination - START");
        }
        APIResponse<Page<Product>> apiResponse = new APIResponse<>();
        Page<Product> productsWithPagination = productService.findProductsWithPagination(offset, pageSize);
        apiResponse.setRecordCount(productsWithPagination.getSize());
        apiResponse.setResponse(productsWithPagination);
        if (log.isInfoEnabled()) {
            log.info("getProductsWithPagination() : Get All Products With Pagination API. Request Param = offset -> {}, pageSize -> {} and Response -> {}",
                    offset, pageSize, ApplicationUtils.getJSONString(apiResponse));
            log.info("getProductsWithPagination() : Get All Products With Pagination - END");
        }
        return apiResponse;
    }

    @GetMapping(value = AppConstants.PRODUCTS_PAGINATION_AND_SORT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private APIResponse<Page<Product>> getProductsWithPaginationAndSort(@RequestParam(value = "offset") int offset,
                                                                        @RequestParam(value = "pageSize") int pageSize,
                                                                        @RequestParam(value = "field") String field) {
        if (log.isInfoEnabled()) {
            log.info("getProductsWithPaginationAndSort() : Get Products With Pagination and Sorting - START");
        }
        APIResponse<Page<Product>> apiResponse = new APIResponse<>();
        Page<Product> productsWithPagination = productService.findProductsWithPaginationAndSorting(offset, pageSize, field);
        apiResponse.setRecordCount(productsWithPagination.getSize());
        apiResponse.setResponse(productsWithPagination);
        if (log.isInfoEnabled()) {
            log.info("getProductsWithPaginationAndSort() : Get Products With Pagination and Sorting. Request Param = offset -> {}, pageSize -> {}, field -> {} and Response -> {}",
                    offset, pageSize, field, ApplicationUtils.getJSONString(apiResponse));
            log.info("getProductsWithPaginationAndSort() : Get Products With Pagination and Sorting - END");
        }
        return apiResponse;
    }

}