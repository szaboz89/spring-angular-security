package com.szabodev.example.security.service;

import com.szabodev.example.security.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    Optional<ProductDTO> findById(Long id);

    List<ProductDTO> findAll();

    void deleteById(Long id);
}
