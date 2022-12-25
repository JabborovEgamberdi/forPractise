package com.example.forlanguage.controller;

import com.example.forlanguage.entity.Product;
import com.example.forlanguage.payload.ApiResponse;
import com.example.forlanguage.payload.ProductDTO;
import com.example.forlanguage.repository.ProductUz;
import com.example.forlanguage.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Product> productList = productService.getAll();
        return new ResponseEntity<>(productList, productList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public HttpEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        ApiResponse addProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @GetMapping("/getUz")
    public HttpEntity<?> getUz() {
        List<ProductUz> allUzProduct = productService.getAllUzProduct();
        return new ResponseEntity<>(allUzProduct, allUzProduct.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

}
