package com.caku.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caku.model.Product;
import com.caku.repository.ProductRepository;



@Service
public class ProductService{

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    
    public void addProduct(Product product) {
    	productRepository.save(product);
    }
    
    public void removeProduct(long id) {
    	productRepository.deleteById(id);
    }
    
    public Optional<Product> getProductById(long id){
    	return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(int id){
    	
    	return productRepository.findAllByCategory_Id(id);
    	
    }
    
}
