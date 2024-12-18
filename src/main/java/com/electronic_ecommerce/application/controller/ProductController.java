package com.electronic_ecommerce.application.controller;

import com.electronic_ecommerce.application.dto.ErrorResponseDto;
import com.electronic_ecommerce.application.dto.PagedProductResponseDto;
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
            @ApiResponse(responseCode = "200", description = "Products obtained", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PagedProductResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid product search", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "There was an unexpected error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "category", description = """
                        Type the kind of product are you looking for.<br>
                        - Currently categories available: \"accessories\", \"clothing\", \"electronics\", \"footwear\", \"home appliances\", \"home & kitchen\", \"sports\",  \"stationery\", \"toys & games\".<br>
                        """,
                        example = "electronics", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.QUERY, name = "page", description = """
                        Shows the page number you want to retrieve (0..N).<br>
                        - Default page is 0.<br>
                        """,
                    example = "0",
                    schema = @Schema(type = "integer", minimum = "0" )),
            @Parameter(in = ParameterIn.QUERY, name = "size", description = """
                        Number of items per page.<br>
                        - Use \"-1\" to get max size.<br>
                        - Only accepts multipliers by 10.<br>
                        - Default size is 10.<br>
                        """,
                    example = "10", schema = @Schema(type = "integer", multipleOf = 10)),
            @Parameter(in = ParameterIn.QUERY, name = "sort",
                    description = """
                        Sorting criteria in the following format: property(;asc|desc).<br>
                        - Multiple sort criteria are supported.<br>
                        - Default sort order is ascending.<br>
                        """,
                    example = "sku;asc",
                    array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "sku;asc")), style = ParameterStyle.SIMPLE)
    })
    @GetMapping
    public ResponseEntity<PagedProductResponseDto> getProducts(@RequestParam(required = false) String category,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String[] sort) {
        log.info("Getting all products with the following request param: category: {}, page: {}, size: {}, sort: {}", category, page, size, sort);
        return ResponseEntity.ok(productService.getAllProducts(category, page, size, sort));
    }
}
