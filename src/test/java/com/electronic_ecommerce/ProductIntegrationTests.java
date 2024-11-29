package com.electronic_ecommerce;

import com.electronic_ecommerce.application.dto.PagedProductResponseDto;
import com.electronic_ecommerce.domain.service.impl.ProductServiceImpl;
import com.electronic_ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ElectronicEcommerceApplication.class)
public class ProductIntegrationTests {

    private static final String URL = "/api/v1/products";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String SORT = "sort";
    private static final String CATEGORY = "category";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void testGetProductsWithoutSort() throws Exception {
        final var mockResponse = new PagedProductResponseDto();
        given(productService.getAllProducts(null, null, null, null)).willReturn(mockResponse);

        final var result = mockMvc
                .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final var responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        verify(productService).getAllProducts(null, null, null, null);
    }

    @Test
    void testGetProductsWithValidSort() throws Exception {
        final var mockResponse = new PagedProductResponseDto();
        given(productService.getAllProducts(null, null, null, new String[]{"sku;asc"})).willReturn(mockResponse);

        final var result = mockMvc
                .perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(SORT, "sku;asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final var responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        verify(productService).getAllProducts(null, null, null, new String[]{"sku;asc"});
    }

    @Test
    void testGetProductsWithCategoryAndPagination() throws Exception {
        final var mockResponse = new PagedProductResponseDto();
        given(productService.getAllProducts("electronics", 1, 20, null)).willReturn(mockResponse);

        final var result = mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(CATEGORY, "electronics")
                        .param(PAGE, "1")
                        .param(SIZE, "20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final var responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        verify(productService).getAllProducts("electronics", 1, 20, null);
    }

    @Test
    void testGetProductsWithMultipleSortCriteria() throws Exception {
        final var mockResponse = new PagedProductResponseDto();
        given(productService.getAllProducts(null, null, null, new String[]{"price;asc", "sku;desc"})).willReturn(mockResponse);

        final var result = mockMvc
                .perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(SORT, "price;asc")
                        .param(SORT, "sku;desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final var responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        verify(productService).getAllProducts(null, null, null, new String[]{"price;asc", "sku;desc"});
    }

    @Test
    void testGetProductsResponseStructure() throws Exception {
        final var product = new Product();
        product.setSku("SKU0050");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setDescription("Test Product");
        product.setCategory("electronics");

        final var mockResponse = new PagedProductResponseDto();
        mockResponse.setPageNumber(0);
        mockResponse.setPageSize(10);
        mockResponse.setTotalPages(1);
        mockResponse.setTotalItems(1);
        mockResponse.setItems(List.of(product));

        given(productService.getAllProducts(null, null, null, null)).willReturn(mockResponse);

        final var result = mockMvc
                .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].sku").value("SKU0050"))
                .andExpect(jsonPath("$.products[0].price").value(BigDecimal.valueOf(100.0)))
                .andExpect(jsonPath("$.products[0].description").value("Test Product"))
                .andExpect(jsonPath("$.products[0].category").value("electronics"))
                .andReturn();

        final var responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
    }
}
