package com.llanerito.manu.service;

import com.llanerito.manu.api.request.CategoryRequest;
import com.llanerito.manu.api.response.CategoryResponse;
import com.llanerito.manu.domain.entities.Category;
import com.llanerito.manu.domain.repositories.CategoryRepository;
import com.llanerito.manu.intrastructure.service.CategoryService;
import com.llanerito.manu.utils.SortType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class); // Mock del repositorio
        categoryService = new CategoryService(categoryRepository); // Inicialización del servicio
    }

    @Test
    void testCreateCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Electronics");

        Category savedCategory = Category.builder().id(1L).name("Electronics").build();
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryResponse response = categoryService.create(request);

        assertNotNull(response);
        assertEquals("Electronics", response.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testGetByIdSuccess() {
        Category mockCategory = Category.builder().id(1L).name("Books").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        CategoryResponse response = categoryService.getById(1L);

        assertNotNull(response);
        assertEquals("Books", response.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void testGetByIdCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.getById(99L));
        verify(categoryRepository).findById(99L);
    }

    @Test
    void testDeleteCategory() {
        Category mockCategory = Category.builder().id(1L).name("Test").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        doNothing().when(categoryRepository).delete(mockCategory);

        categoryService.delete(1L);

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).delete(mockCategory);
    }

    @Test
void testGetAllCategories() {
    int page = 0;
    int size = 10;
    SortType sort = SortType.ASC;

    Category mockCategory = Category.builder().id(1L).name("Category 1").build();
    List<Category> categories = List.of(mockCategory);

    Page<Category> mockPage = new PageImpl<>(categories);
    when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

    Page<CategoryResponse> response = categoryService.getAll(page, size, sort);

    assertNotNull(response);
    assertFalse(response.isEmpty());
    verify(categoryRepository).findAll(any(PageRequest.class));
}

    @Test
    void testCreateCategoryWithEmptyName() {
        CategoryRequest request = new CategoryRequest();
        request.setName(""); // Nombre vacío

        assertThrows(IllegalArgumentException.class, () -> categoryService.create(request));
    }

    @Test
    void testGetAllCategoriesWithNullSort() {
        int page = 0;
        int size = 10;
    
        Category mockCategory = Category.builder().id(1L).name("Category 1").build();
        List<Category> categories = List.of(mockCategory);
    
        Page<Category> mockPage = new PageImpl<>(categories);
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);
    
        Page<CategoryResponse> response = categoryService.getAll(page, size, null);
    
        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(categoryRepository).findAll(any(PageRequest.class));
    }
}