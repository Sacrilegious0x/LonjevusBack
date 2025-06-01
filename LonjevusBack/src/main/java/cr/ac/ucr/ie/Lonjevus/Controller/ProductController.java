package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.service.IProductService;
import cr.ac.ucr.ie.Lonjevus.service.ProductService;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public LinkedList<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product p) {
        productService.addProduct(p);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody Product p) {
        productService.updateProduct(p);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productService.findById(id);
    }
}
