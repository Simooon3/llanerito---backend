package com.llanerito.manu.intrastructure.abstract_service;

import com.llanerito.manu.api.request.categoryRequest;
import com.llanerito.manu.api.response.categoryResponse;

public interface ICategoryService extends CrudDefault <categoryRequest, categoryResponse, Long> {
    public final String FIELD_BY_SORT = "name";
}
