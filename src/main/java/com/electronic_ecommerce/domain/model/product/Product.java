package com.electronic_ecommerce.domain.model.product;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product {

    public static final String SKU = "sku";
    public static final String PRICE = "price";
    public static final String PRICE_DISCOUNT = "priceDiscount";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";

    private Long id;
    private String sku;
    private BigDecimal price;
    private BigDecimal priceDiscount;
    private String description;
    private String category;

}
