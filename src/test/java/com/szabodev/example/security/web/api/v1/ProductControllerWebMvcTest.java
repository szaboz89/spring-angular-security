package com.szabodev.example.security.web.api.v1;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.repository.ProductRepository;
import com.szabodev.example.security.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@TestPropertySource(locations = "classpath:test.properties")
class ProductControllerWebMvcTest {

    @MockBean
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    private ProductDTO product;

    @BeforeEach
    void setUp() {
        product = ProductDTO.builder().id(1L).name("Name").description("Description").price(BigDecimal.TEN).build();
    }

    @Test
    void testFindById() throws Exception {
        given(productService.findById(1L)).willReturn(Optional.of(product));

        mockMvc.perform(get(ProductController.BASE_URL + "/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())));
    }
}
