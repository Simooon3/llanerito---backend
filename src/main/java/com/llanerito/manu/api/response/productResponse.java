package com.llanerito.manu.api.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class productResponse {
    private Long id;
    private String name;
    private String description;
    private String price;
}
