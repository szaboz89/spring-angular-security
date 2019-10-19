package com.szabodev.example.security.service.mapper;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOs(List<Product> products);

    Product toEntity(ProductDTO productDTO);
}
