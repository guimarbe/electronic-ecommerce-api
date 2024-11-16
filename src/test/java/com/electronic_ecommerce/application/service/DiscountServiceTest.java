package com.electronic_ecommerce.application.service;

import com.electronic_ecommerce.domain.enums.Category;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.service.impl.DiscountServiceImpl;
import com.electronic_ecommerce.infraestructure.config.DiscountConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private static final BigDecimal electronics = BigDecimal.valueOf(0.15);
    private static final BigDecimal homeKitchen = BigDecimal.valueOf(0.25);
    private static final BigDecimal specialSku = BigDecimal.valueOf(0.3);


    private DiscountConfig discountConfig;

    private DiscountServiceImpl discountService;

    @BeforeEach
    void setUp() {
        discountConfig = new DiscountConfig(electronics, homeKitchen, specialSku);
        discountService = new DiscountServiceImpl(discountConfig);
    }

    @Test
    void applyDiscount_ShouldApplyElectronicsDiscount() {
        // Given
        final var product = new Product(1L, "SKU001", BigDecimal.valueOf(100.00), BigDecimal.ZERO, "Product 1", "Electronics");

        // When - Then
        final var discountedPrice = discountService.applyDiscount(product);
        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(85.00)));


        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(85.00)));
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(100.00)) < 0, "Discounted price should be less than original price.");
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(80.00)) > 0, "Discounted price should be greater than 80.");
    }

    @Test
    void applyDiscount_ShouldApplyHomeKitchenDiscount() {
        // Given
        final var product = new Product(1L, "SKU002", BigDecimal.valueOf(200), BigDecimal.ZERO,"Product 2", "Home & Kitchen");

        // When - Then
        final var discountedPrice = discountService.applyDiscount(product);
        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(150.00)));
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(200.00)) < 0, "Discounted price should be less than original price.");
    }

    @Test
    void applyDiscount_ShouldApplySpecialSkuDiscount() {
        // Given
        final var product = new Product(1L, "SKU005", BigDecimal.valueOf(300), BigDecimal.ZERO,"Product 3", "misc");

        // When - Then
        final var discountedPrice = discountService.applyDiscount(product);
        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(210.00)));
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(300.00)) < 0, "Discounted price should be less than original price.");
    }

    @Test
    void applyDiscount_ShouldApplyMaximumDiscountWhenMultipleDiscountsApply() {
        // Given
        final var product = new Product(1L, "SKU005", BigDecimal.valueOf(500), BigDecimal.ZERO,"Product 4", "Electronics");

        // When - Then
        final var discountedPrice = discountService.applyDiscount(product);
        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(350.00)));
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(400.00)) < 0, "Discounted price should be less than original price.");
        assertTrue(discountedPrice.compareTo(BigDecimal.valueOf(300.00)) > 0, "Discounted price should be greater than 300.");
    }

    @Test
    void applyDiscount_ShouldReturnOriginalPriceWhenNoDiscountApplies() {
        // Given
        final var product = new Product(1L, "SKU100", BigDecimal.valueOf(400.0), BigDecimal.ZERO,"Product 5", "fashion");

        // When - Then
        final var discountedPrice = discountService.applyDiscount(product);
        assertEquals(0, discountedPrice.compareTo(BigDecimal.valueOf(400.0)));
    }

    @Test
    void getAvailableDiscounts_ShouldIncludeAllHandlers() throws Exception {
        // Given
        final var method = DiscountServiceImpl.class.getDeclaredMethod("getAvailableDiscounts");
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        final var discounts = (Map<String, Function<Product, BigDecimal>>) method.invoke(discountService);

        // When - Then
        assertNotNull(discounts);
        assertEquals(3, discounts.size());
        assertTrue(discounts.containsKey(Category.ELECTRONICS.name()));
        assertTrue(discounts.containsKey(Category.HOME_KITCHEN.name()));
        assertTrue(discounts.containsKey("SpecialSku"));
    }

    @Test
    void getMaxDiscount_ShouldReturnHighestDiscount() throws Exception {
        // Given
        final var product = new Product(1L, "SKU005", BigDecimal.valueOf(100), BigDecimal.ZERO,"Product 6", "Home & Kitchen");
        final var availableDiscounts = new HashMap<String, Function<Product, BigDecimal>>();
        availableDiscounts.put("home_kitchen", productToDiscount -> discountConfig.getHomeKitchen());
        availableDiscounts.put("special_sku", productToDiscount -> discountConfig.getSpecialSku());

        // When - Then
        final var method = DiscountServiceImpl.class.getDeclaredMethod("getMaxDiscount", Product.class, Map.class);
        method.setAccessible(true);
        final var maxDiscount = (BigDecimal) method.invoke(discountService, product, availableDiscounts);
        assertEquals(BigDecimal.valueOf(0.3), maxDiscount);
        assertTrue(maxDiscount.compareTo(BigDecimal.valueOf(0.25)) > 0, "Max discount should be greater than home kitchen discount.");
        assertTrue(maxDiscount.compareTo(BigDecimal.valueOf(0.2)) > 0, "Max discount should be greater than a discount of 0.2.");
    }
}