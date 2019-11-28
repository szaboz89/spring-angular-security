package com.szabodev.example.security.web.api.v1;

import com.szabodev.example.security.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
class ProductControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFindById() {
        ProductDTO[] products = restTemplate.getForObject(ProductController.BASE_URL, ProductDTO[].class);
        assertThat(products).hasSize(2);
    }

    @Test
    void testFindByIdNotFound() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(ProductController.BASE_URL + "/3", String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
