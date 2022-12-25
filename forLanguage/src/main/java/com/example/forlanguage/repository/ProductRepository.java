package com.example.forlanguage.repository;

import com.example.forlanguage.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    List<Product> getProductsByProductNameUzAndAndProductDefinitionUz();
//    List<Product> findByProductNameUzAndProductDefinitionUz();

    @Query(
            value = "select " +
                    "p.productNameUz                as productNameUz, " +
                    "p.productDefinitionUz          as productDefinitionUz " +
                    "from product p"
    )
    List<ProductUz> getProductUzLanguage();

}