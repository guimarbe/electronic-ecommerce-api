package com.electronic_ecommerce.domain.service;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.domain.model.product.Product;

public interface ProductService {

    PagedResponseDto<Product> getAllProducts(String category, Integer page, Integer size, String[] sort);

}
