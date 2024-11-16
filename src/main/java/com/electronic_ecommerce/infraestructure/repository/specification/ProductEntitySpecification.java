package com.electronic_ecommerce.infraestructure.repository.specification;

import com.electronic_ecommerce.domain.enums.Category;
import com.electronic_ecommerce.domain.model.common.CategoryEntity;
import com.electronic_ecommerce.domain.model.product.ProductEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public final class ProductEntitySpecification {

    private static final String CATEGORY = "category";
    private static final String NAME = "name";

    private ProductEntitySpecification() {}

    public static Specification<ProductEntity> hasCategory(final Category category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.equal(root.get(CATEGORY), category) : criteriaBuilder.conjunction();
    }

    public static Specification<ProductEntity> categoryNameContains(final String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return null;
            }
            final var searchPattern = "%" + searchTerm.toUpperCase() + "%";
            final Join<ProductEntity, CategoryEntity> categoryJoin = root.join(CATEGORY);
            return criteriaBuilder.like(criteriaBuilder.upper(categoryJoin.get(NAME)), searchPattern);
        };
    }

}
