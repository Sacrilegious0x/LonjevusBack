/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.jpa.ProductDAOImplements;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import java.util.LinkedList;


public class ProductService {
    
    private static ProductDAOImplements productDAOImplements= new ProductDAOImplements();
    
    public LinkedList<Product> getAllProducts() {
        return productDAOImplements.getAll();
    }
    
    public void addProduct(Product product){
        productDAOImplements.add(product);
    }
} 

