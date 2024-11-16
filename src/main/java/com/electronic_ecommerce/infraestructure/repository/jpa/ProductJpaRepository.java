package com.electronic_ecommerce.infraestructure.repository.jpa;

import com.electronic_ecommerce.domain.model.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    Page<ProductEntity> findAll(Specification<ProductEntity> spec, Pageable pageable);

}
