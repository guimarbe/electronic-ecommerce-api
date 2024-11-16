package com.electronic_ecommerce.application.mapper;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

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

    public Page<Product> convertToDtoPage(Page<ProductEntity> entities) {
        return entities.map(this::convertToDto);
    }

    public PagedResponseDto<Product> toPagedResponseDto(Page<Product> productEntityPage) {
        List<Product> products = new ArrayList<>(productEntityPage.getContent());

        return new PagedResponseDto<>(
                productEntityPage.getNumber(),
                productEntityPage.getSize(),
                productEntityPage.getTotalPages(),
                (int) productEntityPage.getTotalElements(),
                products
        );
    }
}
