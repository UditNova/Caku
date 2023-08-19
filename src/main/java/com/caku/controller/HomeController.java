package com.caku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.caku.service.CategoryService;
import com.caku.service.ProductService;

@Controller
public class HomeController {
    
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    //user->home
    @GetMapping({"/", "/home"})
    public String home(Model model){
        return "index";
    }


    //user->home->shop
    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProducts());
        return "shop";
    }

    //user->home->shop->anycategory
    @GetMapping("/shop/category/{id}")
    public String getProductByCat(@PathVariable int id, Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getProductsByCategory(id));
        return "shop";
    }

    //user->home->shop->viewProduct
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model){
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }


}
