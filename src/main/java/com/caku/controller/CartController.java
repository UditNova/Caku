package com.caku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.caku.global.GlobalData;
import com.caku.model.Product;
import com.caku.service.ProductService;

@Controller
public class CartController {
    
    @Autowired
    ProductService productService;

    //home->shop->product->add to cart
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    //home->cart(after user is authenticated)
    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("cartCount ", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    //to remove product from cart
    //home->cart->remove product
    @GetMapping("/cart/remove/{index}")
    public String removePcart(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    //checkoutPage
    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @PostMapping("/orderPlaced")
    public String orderPlaced(){
        return "orderPlaced";
    }



}
