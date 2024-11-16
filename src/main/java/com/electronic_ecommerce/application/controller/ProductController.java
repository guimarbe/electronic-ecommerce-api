package com.electronic_ecommerce.application.controller;

import com.electronic_ecommerce.application.dto.PagedResponseDto;
import com.electronic_ecommerce.domain.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(final ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products obtained", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid product search", content = @Content),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)
    })
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "category",
                    description = "Valid categories: [\"accessories\", \"clothing\", \"electronics\", \"footwear\", \"home appliances\", \"home & kitchen\", \"sports\",  \"stationery\", \"toys & games\",]"),
            @Parameter(in = ParameterIn.QUERY, name = "page", description = "Results page you want to retrieve (0..N). Default page is 0",
                    schema = @Schema(type = "integer", minimum = "0" )),
            @Parameter(in = ParameterIn.QUERY, name = "size", description = "Number of records per page. Use -1 get max size. Only multipliers by 10. Default size is 10",
                    schema = @Schema(type = "integer", multipleOf = 10)),
            @Parameter(in = ParameterIn.QUERY, name = "sort",
                    description = "Sorting criteria in the following format: property(;asc|desc). Multiple sort criteria are supported. Default sort order is ascending",
                    array = @ArraySchema(schema = @Schema(type = "string")), style = ParameterStyle.SIMPLE)
    })
    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestParam(required = false) String category,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String[] sort) {
        log.info("Getting all products with the following request param: category: {}, page: {}, size: {}, sort: {}", category, page, size, sort);
        return ResponseEntity.ok(productService.getAllProducts(category, page, size, sort));
    }
}
