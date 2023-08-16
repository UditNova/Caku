package com.caku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caku.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
