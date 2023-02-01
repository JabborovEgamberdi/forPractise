package com.example.forlanguage.service;

import com.example.forlanguage.entity.Product;
import com.example.forlanguage.payload.ApiResponse;
import com.example.forlanguage.payload.ProductDTO;
import com.example.forlanguage.repository.ProductRepository;
import com.example.forlanguage.repository.ProductUz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductUz> getAllUzProduct() {
        return productRepository.getProductUzLanguage();
    }

    public ApiResponse addProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setProductNameUz(productDTO.getProductNameUz());
        product.setProductNameRu(productDTO.getProductNameRu());
        product.setProductNameEng(productDTO.getProductNameEng());
        product.setProductDefinitionUz(productDTO.getProductDefinitionUz());
        product.setProductDefinitionRu(productDTO.getProductDefinitionRu());
        product.setProductDefinitionEng(productDTO.getProductDefinitionEng());
        productRepository.save(product);
        return new ApiResponse("Product added successfully", true);
    }

}