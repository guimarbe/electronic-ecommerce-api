package com.electronic_ecommerce.infraestructure.repository.impl;

import com.electronic_ecommerce.application.mapper.ProductMapper;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductEntity;
import com.electronic_ecommerce.domain.model.product.ProductRepository;
import com.electronic_ecommerce.infraestructure.repository.jpa.ProductJpaRepository;
import com.electronic_ecommerce.infraestructure.repository.specification.ProductEntitySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductRepositoryImpl(ProductJpaRepository jpaRepository, ProductMapper productMapper) {
        this.jpaRepository = jpaRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<Product> findProducts(String category, Pageable pageable) {
        Specification<ProductEntity> spec = Specification.where(null);

        if (category != null)
            spec = spec.and(ProductEntitySpecification.categoryNameContains(category));

        final var productEntityPage = jpaRepository.findAll(spec, pageable);
        return productEntityPage.map(productMapper::convertToDto);
    }

}
