/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

/**
 *
 * @author Usuario
 */
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import java.util.LinkedList;

public interface IProductService {
    LinkedList<Product> getAllProducts();
    void addProduct(Product p);
    void updateProduct(Product p);
    void deleteProduct(int id);
    Product findById(int id);
}
