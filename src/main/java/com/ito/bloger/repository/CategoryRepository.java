package com.ito.bloger.repository;

import com.ito.bloger.enitty.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
