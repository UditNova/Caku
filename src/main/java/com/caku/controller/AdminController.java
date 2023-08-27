package com.caku.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.caku.dao.ProductDTO;
import com.caku.model.Category;
import com.caku.model.Product;
import com.caku.service.CategoryService;
import com.caku.service.ProductService;

@Controller
public class AdminController {

    //directory path to save product images
    public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

    //obj of category service
    @Autowired
    CategoryService categoryService;

    //obj of product service
    @Autowired
    ProductService productService;

    //to admin home
    @GetMapping("/admin")
    public String adminHome(){

        return "adminHome";
    }
    
    //admin-> categories
    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    //admin->categories->add
    @GetMapping("/admin/categories/add")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    //admin->categories->add->save
    @PostMapping("/admin/categories/add")
    public String postCategory(@ModelAttribute Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    
    //admin->categories->delete
    @GetMapping("/admin/categories/delete/{id}")
    public String delCategory(@PathVariable int id){
        categoryService.removeCategory(id);
        return "redirect:/admin/categories";
    }

    //admin->categories->update
    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model){
        //checking if there is any category by that id
        Optional<Category> category= categoryService.getCatById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else{
            return "404";
        }
        
    }

    //product section

    //admin->products
    @GetMapping("/admin/products")
    public String products(Model m){
        m.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    //admin->products->addProducts
    @GetMapping("/admin/products/add")
    public String productAddGet(Model m){
        m.addAttribute("productDTO", new ProductDTO());
        m.addAttribute("categories", categoryService.getAllCategory());
        m.addAttribute("isUpdate", false);
        return "productsAdd";
    }
    
    //admin->products->addproducts->submit
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute ProductDTO productDTO, 
    		@RequestParam("productImage")MultipartFile file,
            @RequestParam String imgName)throws IOException{
    	
    	Product product=new Product();
    	product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCatById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID= file.getOriginalFilename();
            Path fileNameAndPath=Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID=imgName;
        }
    	
    	product.setImageName(imageUUID);
        
        //adding/saving the product 
    	productService.addProduct(product);
    	return "redirect:/admin/products";
    }
    
    //if user deletes a product
    //admin->products->delete
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    //if user updates a product
    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model model) {
    Optional<Product> optionalProduct = productService.getProductById(id);

    if (optionalProduct.isPresent()) {
        Product product = optionalProduct.get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("isUpdate", true); // Add this attribute to indicate update mode

        return "productsAdd"; // Use the same template for both add and update
    } else {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("isUpdate", false); // Add this attribute even when product is not found

        return "productsAdd"; // Use the same template to handle the "not found" case
    }
}

    
    
    
 }
