package com.electronic_ecommerce.infraestructure.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "discounts")
public class DiscountConfig {

    private BigDecimal electronics;
    private BigDecimal homeKitchen;
    private BigDecimal specialSku;

}
