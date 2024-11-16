package com.electronic_ecommerce.infraestructure.config;

import com.electronic_ecommerce.application.mapper.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }
}
