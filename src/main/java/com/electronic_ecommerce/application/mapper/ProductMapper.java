package com.electronic_ecommerce.application.mapper;

import com.electronic_ecommerce.application.dto.PagedProductResponseDto;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

public class ProductMapper extends BaseMapper<ProductEntity, Product> {

    @Override
    public ProductEntity convertToEntity(Product dto, Object... args) {
        final var productEntity = new ProductEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, productEntity);
        }
        return productEntity;
    }

    @Override
    public Product convertToDto(ProductEntity entity, Object... args) {
        final var product = new Product();
        if (entity != null) {
            BeanUtils.copyProperties(entity, product);
            product.setCategory(entity.getCategory().getName());
        }
        return product;
    }

    public PagedProductResponseDto toPagedProductResponseDto(Page<Product> productPage) {
        var pagedResponse = new PagedProductResponseDto();
        pagedResponse.setPageNumber(productPage.getNumber());
        pagedResponse.setPageSize(productPage.getSize());
        pagedResponse.setTotalPages(productPage.getTotalPages());
        pagedResponse.setTotalItems((int) productPage.getTotalElements());
        pagedResponse.setItems(productPage.getContent());
        return pagedResponse;
    }
}
