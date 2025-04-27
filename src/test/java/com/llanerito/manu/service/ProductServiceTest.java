package com.llanerito.manu.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import com.llanerito.manu.api.request.ProductRequest;
import com.llanerito.manu.api.response.ProductResponse;
import com.llanerito.manu.domain.entities.Category;
import com.llanerito.manu.domain.entities.Product;
import com.llanerito.manu.domain.repositories.CategoryRepository;
import com.llanerito.manu.domain.repositories.ProductRepository;
import com.llanerito.manu.intrastructure.cloudinary.CloudinaryAdapter;
import com.llanerito.manu.intrastructure.service.ProductService;
import com.llanerito.manu.utils.SortType;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CloudinaryAdapter cloudinaryAdapter; // <-- AQUI LO AGREGAMOS

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = Category.builder()
                .id(1L)
                .name("Electronics")
                .numberProducts(10)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Phone")
                .description("Smartphone")
                .price("999.99")
                .urlImage("image.jpg")
                .category(category)
                .build();

        productRequest = ProductRequest.builder()
                .name("Phone")
                .description("Smartphone")
                .price("999.99")
                .urlImage("image.jpg")
                .idCategory(1L)
                .build();
    }

    @Test
    void testCreateProduct() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(cloudinaryAdapter.uploadImage(anyString())).thenReturn("url-falsa.jpg"); // <-- MOCKEAMOS el uploadImage

        ProductResponse response = productService.create(productRequest);

        assertNotNull(response);
        assertEquals(product.getName(), response.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetByIdSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getById(1L);

        assertNotNull(response);
        assertEquals(product.getName(), response.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.getById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.update(productRequest, 1L);

        assertNotNull(response);
        assertEquals(product.getName(), response.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.update(productRequest, 1L));
    }

    @Test
    void testDeleteProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.delete(1L));
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.delete(1L));
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(product);
        Page<Product> page = new PageImpl<>(products);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<ProductResponse> response = productService.getAll(0, 10, SortType.NONE);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        verify(productRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetByNameCategory() {
        when(productRepository.findByCategory_NameIgnoreCase("Electronics")).thenReturn(List.of(product));

        List<ProductResponse> response = productService.getByNameCategory("Electronics");

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(productRepository, times(1)).findByCategory_NameIgnoreCase("Electronics");
    }
}
