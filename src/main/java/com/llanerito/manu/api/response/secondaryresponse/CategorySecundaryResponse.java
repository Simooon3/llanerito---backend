package com.llanerito.manu.api.response.secondaryresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySecundaryResponse {
    private Long id;
    private String name;
    private int numberProducts;
}
