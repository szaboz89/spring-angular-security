package com.szabodev.example.security.web.api.v1;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

    private MockMvc mockMvc;

    private ProductDTO product;

    @BeforeEach
    void setUp() {
        product = ProductDTO.builder().id(1L).name("Name").description("Description").price(BigDecimal.TEN).build();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetProducts() throws Exception {
        given(productService.findAll()).willReturn(
                Arrays.asList(
                        product,
                        ProductDTO.builder().id(2L).name("Name2").description("Description2").price(BigDecimal.TEN).build()
                )
        );

        mockMvc.perform(get(ProductController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(product.getId().intValue())))
                .andReturn();
    }
}