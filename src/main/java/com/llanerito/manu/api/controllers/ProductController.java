package com.llanerito.manu.api.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.llanerito.manu.api.request.ProductRequest;
import com.llanerito.manu.api.response.ProductResponse;
import com.llanerito.manu.intrastructure.service.ProductService;
import com.llanerito.manu.utils.SortType;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RestController
@RequestMapping(path = "product")
@CrossOrigin(origins = "http://127.0.0.1:5501")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create (@RequestBody ProductRequest ProductRequest){
        return ResponseEntity.ok(this.productService.create(ProductRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(this.productService.getById(id));
    }

    @GetMapping(path = "/category")
    public ResponseEntity<List<ProductResponse>> getProductByCategory(@RequestParam("categoryName") String categoryName){
        List<ProductResponse> products = productService.getByNameCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestHeader(required = false) SortType sortType
    ){
                if(Objects.isNull(sortType)) sortType = SortType.NONE;
                return ResponseEntity.ok(this.productService.getAll(page - 1, size, sortType));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> update(@Validated @PathVariable Long id, @RequestBody ProductRequest request){
        return ResponseEntity.ok(this.productService.update(request, id));
    }
    
}