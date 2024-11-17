package com.electronic_ecommerce.domain.service.impl;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.application.mapper.ProductMapper;
import com.electronic_ecommerce.domain.model.product.Product;
import com.electronic_ecommerce.domain.model.product.ProductRepository;
import com.electronic_ecommerce.domain.service.DiscountService;
import com.electronic_ecommerce.domain.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static com.electronic_ecommerce.application.utils.CompletableFutureUtils.collectResultsFromFutures;
import static com.electronic_ecommerce.application.utils.PaginationUtils.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ExecutorService executorService;
    private final DiscountService discountService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(final ExecutorService executorService, final DiscountService discountService,
                              final ProductRepository productRepository, final ProductMapper productMapper) {
        this.executorService = executorService;
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
        final List<CompletableFuture<Product>> futures = createDiscountFutures(productPage.getContent());
        final List<Product> discountedProducts = collectResultsFromFutures(futures);
        return new PageImpl<>(discountedProducts, productPage.getPageable(), productPage.getTotalElements());
    }

    private List<CompletableFuture<Product>> createDiscountFutures(List<Product> products) {
        return products.stream()
                .map(product -> CompletableFuture.supplyAsync(() -> applyDiscountAsync(product), executorService)
                        .exceptionally(ex -> {
                            log.error("Error applying discount for product: " + product.getId(), ex);
                            return product;
                        }))
                .toList();
    }

    private Product applyDiscountAsync(Product product) {
        final var discountedPrice = discountService.applyDiscount(product);
        product.setPriceDiscount(discountedPrice);
        return product;
    }

}
