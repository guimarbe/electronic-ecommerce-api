package com.electronic_ecommerce.application.utils;

import com.electronic_ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class PaginationUtils {

    private static final String SEMICOLON = ";";
    private static final String DESCENDING = "desc";
    private static final Integer DEFAULT_PAGINATION = 0;
    private static final Integer DEFAULT_SIZE = 10;

    private PaginationUtils() {}

    public static Sort validateSorting(final String[] sort) {
        final var sortBy = convertStringToSort(sort);
        return SortValidator.validate(sortBy, Product.class);
    }

    public static Integer getPageNumber(Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = DEFAULT_PAGINATION;

        return pageNumber;
    }

    public static Integer getPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = DEFAULT_SIZE;
        } else if (pageSize.equals(-1)) {
            pageSize = Integer.MAX_VALUE;
        }
        return pageSize;
    }

    private static Sort convertStringToSort(final String[] sort) {
        if (sort == null || sort.length == 0) {
            return Sort.by(Product.SKU, Product.PRICE, Product.DESCRIPTION, Product.CATEGORY).ascending();
        }

        final List<Sort.Order> orders = Arrays.stream(sort)
                .filter(Objects::nonNull)
                .map(sortParam -> sortParam.split(SEMICOLON))
                .filter(parts -> parts.length > 0 && !parts[0].isEmpty())
                .map(parts -> new Sort.Order(
                        parts.length > 1 && DESCENDING.equalsIgnoreCase(parts[1]) ? Sort.Direction.DESC : Sort.Direction.ASC,
                        parts[0]))
                .toList();

        return orders.isEmpty()
                ? Sort.by(Product.SKU, Product.PRICE, Product.DESCRIPTION, Product.CATEGORY).ascending()
                : Sort.by(orders);
    }

}
