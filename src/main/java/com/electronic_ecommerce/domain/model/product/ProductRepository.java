package com.electronic_ecommerce.domain.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Page<Product> findProducts(String category, Pageable pageable);

}
