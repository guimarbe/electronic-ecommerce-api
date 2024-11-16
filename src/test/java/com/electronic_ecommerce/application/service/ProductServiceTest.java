package com.electronic_ecommerce.application.service;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.application.mapper.ProductMapper;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductRepository;
import com.electronic_ecommerce.domain.service.impl.DiscountServiceImpl;
import com.electronic_ecommerce.domain.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import static com.electronic_ecommerce.utils.ProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private DiscountServiceImpl discountService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(discountService, productRepository, productMapper);
    }

    @Test
    void getAllProducts_ShouldReturnPagedResponseDto() {
        // Given
        final var category = "electronics";
        final var productPage = productPageSample();
        final var expectedResponse = pagedResponseDtoSample();

        // When
        when(productRepository.findProducts(eq(category), any(Pageable.class))).thenReturn(productPage);
        when(discountService.applyDiscount(any(Product.class))).thenReturn(BigDecimal.valueOf(90));
        when(productMapper.toPagedResponseDto(productPage)).thenReturn(expectedResponse);

        // Then
        PagedResponseDto<Product> result = productService.getAllProducts(category, PAGE, SIZE, SORT);

        assertNotNull(result);
        assertEquals(expectedResponse.getPageNumber(), result.getPageNumber());
        assertEquals(expectedResponse.getPageSize(), result.getPageSize());
        assertEquals(expectedResponse.getTotalNumberItems(), result.getTotalNumberItems());
        assertEquals(expectedResponse.getItems(), result.getItems());
        verify(productRepository).findProducts(eq(category), any(Pageable.class));
        verify(discountService, times(productListSample().size())).applyDiscount(any(Product.class));
        verify(productMapper).toPagedResponseDto(productPage);
    }

    @Test
    void getAllProducts_ShouldApplyDiscountsToAllProducts() {
        // Given
        final var category = "furniture";
        final String[] sort = {"description;desc"};

        final List<Product> productList = List.of(
                new Product(1L, "SKU100", BigDecimal.valueOf(300), BigDecimal.ZERO, "Table", "furniture"),
                new Product(2L, "SKU101", BigDecimal.valueOf(500), BigDecimal.ZERO, "Chair", "furniture")
        );

        Page<Product> productPage = new PageImpl<>(productList);

        // When
        when(productRepository.findProducts(eq(category), any(Pageable.class))).thenReturn(productPage);
        when(discountService.applyDiscount(productList.getFirst())).thenReturn(BigDecimal.valueOf(270));
        when(discountService.applyDiscount(productList.getLast())).thenReturn(BigDecimal.valueOf(450));

        // Then
        productService.getAllProducts(category, PAGE, SIZE, sort);

        assertEquals(BigDecimal.valueOf(270), productList.getFirst().getPriceDiscount());
        assertEquals(BigDecimal.valueOf(450), productList.getLast().getPriceDiscount());
        verify(discountService).applyDiscount(productList.getFirst());
        verify(discountService).applyDiscount(productList.getLast());
    }

    @Test
    void getAllProducts_WithNullSort_ShouldUseDefaultSort() {
        // Given
        final List<Product> productList = productListSample();
        final Page<Product> productPage = new PageImpl<>(productList);
        final PagedResponseDto<Product> expectedResponse = new PagedResponseDto<>();
        expectedResponse.setPageNumber(null);
        expectedResponse.setPageSize(null);
        expectedResponse.setTotalNumberItems(productList.size());
        expectedResponse.setItems(productList);

        // When
        when(productRepository.findProducts(eq(null), any(Pageable.class))).thenReturn(productPage);
        when(productMapper.toPagedResponseDto(productPage)).thenReturn(expectedResponse);

        // Then
        final PagedResponseDto<Product> result = productService.getAllProducts(null, null, null, null);

        assertNotNull(result);
        verify(productRepository).findProducts(eq(null), any(Pageable.class));
    }

    @Test
    void checkAndApplyAvailableDiscounts_ShouldApplyDiscountsCorrectly() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        // Given
        final List<Product> productList = List.of(
                new Product(1L, "SKU001", BigDecimal.valueOf(100), BigDecimal.ZERO, "Product A", "category"),
                new Product(2L, "SKU002", BigDecimal.valueOf(200), BigDecimal.ZERO, "Product B", "category")
        );

        Page<Product> productPage = new PageImpl<>(productList);

        // When
        when(discountService.applyDiscount(productList.getFirst())).thenReturn(BigDecimal.valueOf(90));
        when(discountService.applyDiscount(productList.getLast())).thenReturn(BigDecimal.valueOf(180));

        // Then
        final var method = ProductServiceImpl.class.getDeclaredMethod("checkAndApplyAvailableDiscounts", Page.class);
        method.setAccessible(true);
        Page<Product> result = (Page<Product>) method.invoke(productService, productPage);

        assertEquals(BigDecimal.valueOf(90), result.getContent().get(0).getPriceDiscount());
        assertEquals(BigDecimal.valueOf(180), result.getContent().get(1).getPriceDiscount());
        verify(discountService).applyDiscount(productList.getFirst());
        verify(discountService).applyDiscount(productList.getLast());
    }
}