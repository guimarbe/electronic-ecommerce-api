package com.electronic_ecommerce.domain.model.product;

import com.electronic_ecommerce.domain.model.common.CategoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ecommerce_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @Column(nullable = false)
    @Min(value = 0)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    @Min(value = 0)
    private BigDecimal priceDiscount;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private CategoryEntity category;

}
