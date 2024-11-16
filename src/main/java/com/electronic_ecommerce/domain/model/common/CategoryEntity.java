package com.electronic_ecommerce.domain.model.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ecommerce_category")
public class CategoryEntity {

    @Id
    private Integer id;
    private String name;

}