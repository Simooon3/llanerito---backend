package com.llanerito.manu.intrastructure.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.llanerito.manu.api.request.ProductRequest;
import com.llanerito.manu.api.response.ProductResponse;
import com.llanerito.manu.api.response.secundaryResponse.CategorySecundaryResponse;
import com.llanerito.manu.domain.entities.Category;
import com.llanerito.manu.domain.entities.Product;
import com.llanerito.manu.domain.repositories.CategoryRepository;
import com.llanerito.manu.domain.repositories.ProductRepository;
import com.llanerito.manu.intrastructure.abstract_service.IProductService;
import com.llanerito.manu.intrastructure.cloudinary.CloudinaryAdapter;
import com.llanerito.manu.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    @Autowired
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = this.requestToEntity(request);
        CloudinaryAdapter cloudinary = new CloudinaryAdapter();
        String imageUrl = cloudinary.uploadImage(request.getUrlImage());
        product.setUrlImage(imageUrl);  
        return this.entityToResponse(this.productRepository.save(product));
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow();
        return this.entityToResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest request, Long id) {
        Product product = this.getByIdProduct(id);
        Product productUpdate = new Product();
        if (product != null) {   
            productUpdate = this.requestToEntity(request);
            productUpdate.setId(id);
        }
            return this.entityToResponse(this.productRepository.save(productUpdate));

    }

    @Override
    public void delete(Long id) {
        Product product = this.getByIdProduct(id);
        this.productRepository.delete(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size, SortType sort) {
        if(page < 0) page = 0;
        PageRequest pagination = null;
        switch(sort){
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.productRepository.findAll(pagination).map(this::entityToResponse);
    }

    public List<ProductResponse> getByNameCategory(String name){
        return this.listToListResponse(this.productRepository.findByCategory_NameIgnoreCase(name));
    }

    private List<ProductResponse> listToListResponse(List<Product> productList){
        List<ProductResponse> products = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .urlImage(product.getUrlImage())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(this.convertCategory(product.getCategory())).build();
            products.add(productResponse);
        }
        return products;
    }

    private Product requestToEntity(ProductRequest request){
        return Product.builder()
        .urlImage(request.getUrlImage())
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .category(this.getByIdCategory(request.getIdCategory())).build();
    }

    private ProductResponse entityToResponse(Product entity){
        return ProductResponse.builder()
        .id(entity.getId())
        .urlImage(entity.getUrlImage())
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .category(this.convertCategory(entity.getCategory())).build();
    }

    private Product getByIdProduct(Long id){
        Product product = this.productRepository.findById(id).orElseThrow();
        return product;
    }

    private  Category getByIdCategory(Long id){
        Category category = this.categoryRepository.findById(id).orElseThrow();
        return category;
    }

    private CategorySecundaryResponse convertCategory(Category category){
        return CategorySecundaryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .numberProducts(category.getNumberProducts()).build();
    }

}
