package com.llanerito.manu.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.llanerito.manu.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
