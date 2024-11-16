package com.electronic_ecommerce.application.utils;

import com.electronic_ecommerce.application.Exceptions.InvalidSortFieldException;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class SortValidator {

    private SortValidator() {}

    public static Sort validate(Sort sort, Class<?> entityClass) {
        List<String> validFields = Arrays.stream(entityClass.getDeclaredFields())
                .map(Field::getName)
                .toList();

        sort.stream().forEach(order -> {
            if (!validFields.contains(order.getProperty())) {
                throw new InvalidSortFieldException("Invalid sort field: " + order.getProperty());
            }
        });

        return sort;
    }
}