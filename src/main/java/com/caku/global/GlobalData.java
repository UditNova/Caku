package com.caku.global;

import java.util.ArrayList;
import java.util.List;

import com.caku.model.Product;

public class GlobalData {
    
    public static List<Product> cart;
    static{
        cart=new ArrayList<Product>();
    }
    
}
