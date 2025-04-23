package com.llanerito.manu.intrastructure.abstract_service;

import com.llanerito.manu.api.request.ProductRequest;
import com.llanerito.manu.api.response.ProductResponse;

public interface IProductService extends CrudDefault<ProductRequest, ProductResponse, Long>{
    public final String FIELD_BY_SORT = "name";
}
