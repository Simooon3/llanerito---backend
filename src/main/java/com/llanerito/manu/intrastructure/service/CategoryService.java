package com.llanerito.manu.intrastructure.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.llanerito.manu.api.request.categoryRequest;
import com.llanerito.manu.api.response.categoryResponse;
import com.llanerito.manu.intrastructure.abstract_service.ICategoryService;
import com.llanerito.manu.utils.SortType;

@Service
public class CategoryService implements ICategoryService {

    @Override
    public categoryResponse create(categoryRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public categoryResponse getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public categoryResponse update(categoryRequest request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Page<categoryResponse> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

}
