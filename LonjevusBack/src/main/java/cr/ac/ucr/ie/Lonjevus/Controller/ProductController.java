package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.service.ProductService;
import java.util.LinkedList;
import org.springframework.web.bind.annotation.*;

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
