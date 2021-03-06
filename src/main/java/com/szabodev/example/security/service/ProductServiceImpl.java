package com.szabodev.example.security.service;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.model.Product;
import com.szabodev.example.security.repository.ProductRepository;
import com.szabodev.example.security.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product saved = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDTO(saved);
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        return Optional.ofNullable(productMapper.toDTO(byId.orElse(null)));
    }

    @Override
    public List<ProductDTO> findAll() {
        return productMapper.toDTOs((List<Product>) productRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
