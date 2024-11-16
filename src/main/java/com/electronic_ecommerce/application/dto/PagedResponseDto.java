package com.electronic_ecommerce.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponseDto<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalNumberItems;
    private List<T> items;

}
