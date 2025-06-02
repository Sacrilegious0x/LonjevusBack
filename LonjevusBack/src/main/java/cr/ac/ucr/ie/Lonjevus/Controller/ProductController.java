package cr.ac.ucr.ie.Lonjevus.Controller;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.domain.Unit;
import cr.ac.ucr.ie.Lonjevus.service.IProductService;
import cr.ac.ucr.ie.Lonjevus.service.ISupplierService;
import cr.ac.ucr.ie.Lonjevus.service.IUnitService;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("products")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final LocalStorageService localStorageService;

    @Autowired
    public ProductController(LocalStorageService localStorageService) {
        this.localStorageService = localStorageService;
    }

    @Autowired
    private IProductService productService;

    @Autowired
    private IUnitService unitService;

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("/list")
    public Map<String, Object> getList() {
        return Collections.singletonMap("products", productService.getAllProducts());
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> saveProduct(
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @RequestParam("expirationDate") String expirationDate, // ISO format: yyyy-MM-dd
            @RequestParam("unitId") int unitId,
            @RequestParam("supplierId") int supplierId,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        Unit unit = unitService.getById(unitId);
        Supplier supplier = supplierService.getById(supplierId);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setExpirationDate(LocalDate.parse(expirationDate));
        product.setUnit(unit);
        product.setSupplier(supplier);

        if (photo != null) {
            String photoPath = localStorageService.save(photo);
            product.setPhotoURL(photoPath);
        }

        productService.save(product);
        return getList();
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteProduct(@RequestParam int id) {
        productService.delete(id);
        return getList();
    }

    @GetMapping("/getById")
    public Product getProductById(@RequestParam int id) {
        return productService.getById(id);
    }

    @PostMapping(path = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> updateProduct(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @RequestParam("expirationDate") String expirationDate,
            @RequestParam("unitId") int unitId,
            @RequestParam("supplierId") int supplierId,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        Unit unit = unitService.getById(unitId);
        Supplier supplier = supplierService.getById(supplierId);

        Product existing = productService.getById(id);

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setExpirationDate(LocalDate.parse(expirationDate));
        product.setUnit(unit);
        product.setSupplier(supplier);

        if (photo != null) {
            String photoPath = localStorageService.save(photo);
            product.setPhotoURL(photoPath);
        } else {
            product.setPhotoURL(existing.getPhotoURL());
        }

        productService.save(product);
        return getList();
    }
}
