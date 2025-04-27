package com.llanerito.manu.intrastructure.abstract_service;

import org.springframework.data.domain.Page;

import com.llanerito.manu.utils.SortType;

public interface CrudDefault<R, S, I> {
    public S create(R request);
    public S getById(I id);
    public S update(R request, I id);
    public void delete(I id);
    public Page<S> getAll(int page, int size, SortType sort);
}

