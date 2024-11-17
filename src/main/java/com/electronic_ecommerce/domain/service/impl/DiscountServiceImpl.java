package com.electronic_ecommerce.domain.service.impl;

import com.electronic_ecommerce.application.Exceptions.DiscountApplicationException;
import com.electronic_ecommerce.domain.enums.Category;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.service.DiscountService;
import com.electronic_ecommerce.infraestructure.config.DiscountConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {

    private static final String SPECIAL_SKU = "SpecialSku";
    private static final String FIVE_ENDING_SKU = "5";

    private final DiscountConfig discountConfig;

    @Autowired
    public DiscountServiceImpl(final DiscountConfig discountConfig) {
        this.discountConfig = discountConfig;
    }

    public BigDecimal applyDiscount(final Product product) {
        try {
            final Map<String, Function<Product, BigDecimal>> discountHandlers = getAvailableDiscounts();
            final var maxDiscount = getMaxDiscount(product, discountHandlers);
            return product.getPrice().subtract(product.getPrice().multiply(maxDiscount))
                    .setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("An unexpected error occurred while applying a discount: {}", e.getMessage());
            throw new DiscountApplicationException(e.getMessage());
        }
    }

    /**
     *  We can use this method to call a possible DiscountRepository here to get the available discounts on the Database,
     *  Howevere, we're using these 3 static discounts for
     */
    private Map<String, Function<Product, BigDecimal>> getAvailableDiscounts() {
        final Map<String, Function<Product, BigDecimal>> discounts = new HashMap<>();
        discounts.put(Category.ELECTRONICS.name(), this::applyElectronicsDiscount);
        discounts.put(Category.HOME_KITCHEN.name(), this::applyHomeKitchenDiscount);
        discounts.put(SPECIAL_SKU, this::applySpecialSkuDiscount);
        return discounts;
    }

    private BigDecimal getMaxDiscount(final Product product, final Map<String, Function<Product, BigDecimal>> discounts) {
        var maxDiscount = BigDecimal.ZERO;

        for (Function<Product, BigDecimal> handler : discounts.values()) {
            BigDecimal discount = handler.apply(product);
            maxDiscount = maxDiscount.max(discount);
        }

        return maxDiscount;
    }

    private BigDecimal applyElectronicsDiscount(final Product product) {
        if (product.getCategory() != null && Category.ELECTRONICS.getDescription().equals(product.getCategory())) {
            return discountConfig.getElectronics();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal applyHomeKitchenDiscount(final Product product) {
        if (product.getCategory() != null && Category.HOME_KITCHEN.getDescription().equals(product.getCategory())) {
            return discountConfig.getHomeKitchen();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal applySpecialSkuDiscount(final Product product) {
        if (product.getSku().endsWith(FIVE_ENDING_SKU)) {
            return discountConfig.getSpecialSku();
        }
        return BigDecimal.ZERO;
    }
}
