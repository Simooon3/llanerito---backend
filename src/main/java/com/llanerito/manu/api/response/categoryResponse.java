package com.llanerito.manu.api.response;

import java.util.List;

import com.llanerito.manu.api.response.secundaryResponse.ProductSecundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private int numberProducts;
    private List<ProductSecundaryResponse> productList;
}
