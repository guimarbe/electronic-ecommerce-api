package com.electronic_ecommerce.application.controller;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.domain.service.impl.ProductServiceImpl;
import com.electronic_ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
    }

    @Test
    void shouldGetProducts() {
        final var pagedResponseDto = new PagedResponseDto<Product>();
        when(productService.getAllProducts(any(), any(), any(), any())).thenReturn(pagedResponseDto);
        assertEquals(HttpStatus.OK, productController.getProducts(null, null, null, null).getStatusCode());
    }
}