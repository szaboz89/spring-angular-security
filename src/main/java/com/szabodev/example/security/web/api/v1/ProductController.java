package com.szabodev.example.security.web.api.v1;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    static final String BASE_URL = "/api/v1/books";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> products() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO product) {
        log.info("Saving: {}", product);
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<ProductDTO> byId = productService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Delete product with id: {}", id);
        Optional<ProductDTO> byId = productService.findById(id);
        if (byId.isPresent()) {
            productService.deleteById(byId.get().getId());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
