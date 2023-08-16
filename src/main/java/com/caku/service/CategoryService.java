package com.caku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caku.model.Category;
import com.caku.repository.CategoryRepository;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository catrepo;
    
    //methods
    
    //to save Category
    public void addCategory(Category category){
        catrepo.save(category);
    }

    //to fetch all Category
    public List<Category> getAllCategory(){
        return catrepo.findAll();
    }

    //to delete a category
    public void removeCategory(int id){
        catrepo.deleteById(id);
    }

    //to get category
    public Optional<Category> getCatById(int id){
        return catrepo.findById(id);
    }

}
