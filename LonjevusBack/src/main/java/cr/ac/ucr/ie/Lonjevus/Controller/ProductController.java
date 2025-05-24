/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.service.ProductService;
import java.util.LinkedList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@CrossOrigin(origins = "http://localhost:5173") //Mi puerto del front
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService = new ProductService();

    //Listar todos mis productos
    @GetMapping("/all")
    public LinkedList<Product> getAllProducts() {
        return productService.getAllProducts();
    }

}
