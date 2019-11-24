package com.szabodev.example.security;

import com.szabodev.example.security.model.Product;
import com.szabodev.example.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
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
    public CommandLineRunner commandLineRunner(ProductRepository productRepository, @Value("${config.environment}") String environment) {
        return args -> {
            if ("local".equals(environment) && productRepository.count() == 0) {
                productRepository.save(Product.builder().name("product1").description("description1").price(BigDecimal.TEN).build());
                productRepository.save(Product.builder().name("product2").description("description2").price(BigDecimal.ONE).build());
            }
        };
    }
}
