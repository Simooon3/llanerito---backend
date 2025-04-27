package com.llanerito.manu.intrastructure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.llanerito.manu.api.request.CategoryRequest;
import com.llanerito.manu.api.response.CategoryResponse;
import com.llanerito.manu.api.response.secondaryresponse.ProductSecundaryResponse;
import com.llanerito.manu.domain.entities.Category;
import com.llanerito.manu.domain.repositories.CategoryRepository;
import com.llanerito.manu.intrastructure.abstract_service.ICategoryService;
import com.llanerito.manu.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private static final String FIELD_BY_SORT = "name"; // Define el campo por el cual se ordenará.

    @Override
    public CategoryResponse create(CategoryRequest request) {
        // Validación para manejar nombres nulos o vacíos
        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("The category name cannot be null or empty");
        }
        Category category = this.requestToEntity(request);
        return this.entityToResponse(this.categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return this.entityToResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryRequest request, Long id) {
        Category categoryUpdate = this.requestToEntity(request);
        categoryUpdate.setId(id);
        return this.entityToResponse(this.categoryRepository.save(categoryUpdate));
    }

    @Override
    public void delete(Long id) {
        Category category = this.getByIdCategory(id);
        this.categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryResponse> getAll(int page, int size, SortType sort) {
        PageRequest pagination;
        if (page < 0) page = 0;

        // Validación para manejar valores nulos en "sort"
        if (sort == null) {
            sort = SortType.NONE; // Valor por defecto
        }

        switch (sort) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> throw new IllegalArgumentException("Invalid sort type");
        }

        return this.categoryRepository.findAll(pagination).map(this::entityToResponse);
    }

    private Category requestToEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }

    private CategoryResponse entityToResponse(Category entity) {
        List<ProductSecundaryResponse> productList = new ArrayList<>();

        if (entity.getProducts() != null) {
            productList = entity.getProducts().stream().map(productEntity -> {
                ProductSecundaryResponse product = new ProductSecundaryResponse();
                BeanUtils.copyProperties(productEntity, product);
                return product;
            }).collect(Collectors.toList());
        }

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberProducts(entity.getNumberProducts())
                .productList(productList)
                .build();
    }

    private Category getByIdCategory(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}