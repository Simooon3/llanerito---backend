package com.llanerito.manu.api.response;

import com.llanerito.manu.api.response.secondaryresponse.CategorySecundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String urlImage;
    private String name;
    private String description;
    private String price;
    private CategorySecundaryResponse category;
}
