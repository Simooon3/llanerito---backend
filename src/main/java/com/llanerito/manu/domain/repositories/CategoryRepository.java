package com.llanerito.manu.domain.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.llanerito.manu.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
