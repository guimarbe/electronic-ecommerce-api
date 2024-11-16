package com.electronic_ecommerce.domain.service;

import com.electronic_ecommerce.domain.model.product.Product;

import java.math.BigDecimal;

public interface DiscountService {

    BigDecimal applyDiscount(final Product product);

}
