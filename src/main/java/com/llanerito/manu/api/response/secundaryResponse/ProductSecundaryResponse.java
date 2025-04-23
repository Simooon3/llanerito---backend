package com.llanerito.manu.api.response.secundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSecundaryResponse {
    private Long id;
    private String name;
    private String urlImage;
    private String description;
    private String price;
}
