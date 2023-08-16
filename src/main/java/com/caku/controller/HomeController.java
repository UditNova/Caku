package com.caku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.caku.service.CategoryService;
import com.caku.service.ProductService;

@Controller
public class HomeController {
    
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    @GetMapping({"/", "/home"})
    public String home(Model model){
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());

        return "shop";
    }

}
