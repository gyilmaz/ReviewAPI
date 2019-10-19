package com.reviews.controller;

import com.reviews.model.Product;
import com.reviews.repository.jpa.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Spring REST com.reviews.controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductRepository productRepository;

    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody Product product){
        productRepository.save(product);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
        if(productRepository.existsById(id)){
            return ResponseEntity.of(productRepository.findById(id));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @GetMapping(value = "/")
    public List<Product> listProducts() {
        return productRepository.findAll();
    }
}