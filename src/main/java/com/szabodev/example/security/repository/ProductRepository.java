package com.szabodev.example.security.repository;

import com.szabodev.example.security.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
