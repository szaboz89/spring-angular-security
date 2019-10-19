package com.szabodev.example.security;

import com.szabodev.example.security.dto.ProductDTO;
import com.szabodev.example.security.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringAngularSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularSecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ProductService productService) {
        return args -> {
            productService.save(ProductDTO.builder().name("product1").price(BigDecimal.TEN).build());
            productService.save(ProductDTO.builder().name("product2").price(BigDecimal.ONE).build());
        };
    }
}
