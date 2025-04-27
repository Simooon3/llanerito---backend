package com.llanerito.manu.api.controllers;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.llanerito.manu.api.request.CategoryRequest;
import com.llanerito.manu.api.response.CategoryResponse;
import com.llanerito.manu.intrastructure.service.CategoryService;
import com.llanerito.manu.utils.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "category")
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(this.categoryService.create(categoryRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestHeader(required = false) SortType sortType
    ) {
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        return ResponseEntity.ok(this.categoryService.getAll(page - 1, size, sortType));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> update(@Validated @RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.update(categoryRequest, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
