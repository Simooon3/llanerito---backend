package com.llanerito.manu.intrastructure.abstract_service;

import com.llanerito.manu.api.request.productRequest;
import com.llanerito.manu.api.response.productResponse;
import com.llanerito.manu.intrastructure.abstract_service.services.CrudDefault;

public interface IProductService extends CrudDefault<productRequest, productResponse, Long>{
    public final String FIELD_BY_SORT = "name";
}
