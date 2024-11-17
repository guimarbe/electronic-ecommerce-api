package com.electronic_ecommerce.utils;

import com.electronic_ecommerce.application.dto.PagedProductResponseDto;
import com.electronic_ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public final class ProductConstants {

    public static final Integer PAGE = 0;
    public static final Integer SIZE = 10;
    public static final String[] SORT = {"price;asc"};

    private ProductConstants() {}

    public static List<Product> productListSample() {
        return List.of(
                new Product(1L,"SKU001", BigDecimal.valueOf(100), BigDecimal.ZERO, "Product 1", "electronics"),
                new Product(2L, "SKU002", BigDecimal.valueOf(200), BigDecimal.ZERO, "Product 2", "electronics")
        );
    }

    public static Page<Product> productPageSample() {
        return new PageImpl<>(productListSample());
    }

    public static PagedProductResponseDto pagedProductResponseDtoSample() {
        final var pagedResponseDto = new PagedProductResponseDto();
        pagedResponseDto.setPageNumber(PAGE);
        pagedResponseDto.setPageSize(SIZE);
        pagedResponseDto.setTotalItems(productListSample().size());
        pagedResponseDto.setItems(productListSample());
        return pagedResponseDto;
    }
}
