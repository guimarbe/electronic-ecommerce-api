package com.electronic_ecommerce.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public abstract class PagedResponseDto<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalItems;

    @JsonIgnore
    private List<T> items;

    public PagedResponseDto(Integer pageNumber, Integer pageSize, Integer totalPages, Integer totalItems, List<T> items) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.items = items;
    }

}
