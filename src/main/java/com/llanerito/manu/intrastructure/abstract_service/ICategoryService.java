package com.llanerito.manu.intrastructure.abstract_service;

import com.llanerito.manu.api.request.CategoryRequest;
import com.llanerito.manu.api.response.CategoryResponse;

public interface ICategoryService extends CrudDefault <CategoryRequest, CategoryResponse, Long> {
    public final String FIELD_BY_SORT = "name";
}
