package com.electronic_ecommerce.domain.service.impl;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.application.mapper.ProductMapper;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductRepository;
import com.electronic_ecommerce.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.electronic_ecommerce.application.utils.PaginationUtils.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final DiscountServiceImpl discountService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(final DiscountServiceImpl discountService, final ProductRepository productRepository,
                              final ProductMapper productMapper) {
        this.discountService = discountService;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public PagedResponseDto<Product> getAllProducts(String category, Integer page, Integer size, String[] sort) {
        final var validateSort = validateSorting(sort);
        final var pageNumber = getPageNumber(page);
        final var pageSize = getPageSize(size);
        final var pageable = PageRequest.of(pageNumber, pageSize, validateSort);
        final var productsPage = checkAndApplyAvailableDiscounts(productRepository.findProducts(category, pageable));
        return productMapper.toPagedResponseDto(productsPage);
    }

    private Page<Product> checkAndApplyAvailableDiscounts(final Page<Product> productPage) {
        productPage.getContent().forEach(product -> {
            final var discountedPrice = discountService.applyDiscount(product);
            product.setPriceDiscount(discountedPrice);
        });

        return productPage;
    }

}
