package com.caku.dao;
import lombok.Data;

@Data
public class ProductDTO {
    
    private long id;
    private String name;
    private int categoryId;
    private double weight;
    private double price;
    private String description;
    private String imageName;


}
