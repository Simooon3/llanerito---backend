package com.llanerito.manu.intrastructure.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.llanerito.manu.api.request.ProductRequest;
import com.llanerito.manu.api.response.ProductResponse;
import com.llanerito.manu.api.response.secondaryresponse.CategorySecundaryResponse;
import com.llanerito.manu.domain.entities.Category;
import com.llanerito.manu.domain.entities.Product;
import com.llanerito.manu.domain.repositories.CategoryRepository;
import com.llanerito.manu.domain.repositories.ProductRepository;
import com.llanerito.manu.intrastructure.abstract_service.IProductService;
import com.llanerito.manu.intrastructure.cloudinary.CloudinaryAdapter;
import com.llanerito.manu.intrastructure.exception.ProductNotFoundException;
import com.llanerito.manu.utils.SortType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryAdapter cloudinaryAdapter; // Inyectado


    private static final String FIELD_BY_SORT = "price"; // (Por ejemplo, o el campo que usas para ordenar)

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = this.requestToEntity(request);
        String imageUrl = this.cloudinaryAdapter.uploadImage(request.getUrlImage());
        product.setUrlImage(imageUrl);
        return this.entityToResponse(this.productRepository.save(product));
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = this.productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        return this.entityToResponse(product);
    }
    

    @Override
    public ProductResponse update(ProductRequest request, Long id) {
        Product product = this.getByIdProduct(id);
        if (product != null) {
            Product productUpdate = this.requestToEntity(request);
            productUpdate.setId(id);
            return this.entityToResponse(this.productRepository.save(productUpdate));
        }
        throw new ProductNotFoundException("Product not found"); // Esto es para evitar que retorne null
    }

    @Override
    public void delete(Long id) {
        Product product = this.getByIdProduct(id);
        this.productRepository.delete(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size, SortType sort) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sort) {
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> PageRequest.of(page, size);
        };

        return this.productRepository.findAll(pagination).map(this::entityToResponse);
    }

    public List<ProductResponse> getByNameCategory(String name) {
        return this.listToListResponse(this.productRepository.findByCategory_NameIgnoreCase(name));
    }

    private List<ProductResponse> listToListResponse(List<Product> productList) {
        List<ProductResponse> products = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getId())
                    .urlImage(product.getUrlImage())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .category(this.convertCategory(product.getCategory()))
                    .build();
            products.add(productResponse);
        }
        return products;
    }

    private Product requestToEntity(ProductRequest request) {
        return Product.builder()
                .urlImage(request.getUrlImage())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(this.getByIdCategory(request.getIdCategory()))
                .build();
    }

    private ProductResponse entityToResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .urlImage(entity.getUrlImage())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(this.convertCategory(entity.getCategory()))
                .build();
    }

    private Product getByIdProduct(Long id) {
        return this.productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
    }
    

    private Category getByIdCategory(Long id) {
        return this.categoryRepository.findById(id).orElseThrow();
    }

    private CategorySecundaryResponse convertCategory(Category category) {
        return CategorySecundaryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .numberProducts(category.getNumberProducts())
                .build();
    }
}
