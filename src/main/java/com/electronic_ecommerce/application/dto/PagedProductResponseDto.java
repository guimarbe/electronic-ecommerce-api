package com.electronic_ecommerce.application.dto;

import com.electronic_ecommerce.domain.model.product.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "PagedProductResponseDto", description = "Paged response containing a list of products")
public class PagedProductResponseDto extends PagedResponseDto<Product> {

    @JsonProperty("products")
    @Override
    public List<Product> getItems() {
        return super.getItems();
    }

    @JsonProperty("products")
    @Override
    public void setItems(List<Product> items) {
        super.setItems(items);
    }
}
