package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.service.IInventoryService;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    @Autowired
    private IInventoryService service;

    @Autowired
    private LocalStorageService localStorageService;
    
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_VIEW')")
    @GetMapping("/all")
    public Map<String, Object> getList() {
        return Collections.singletonMap("inventory", service.getAll());
    }
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_CREATE')")
    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> saveInventory(
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category,
            @RequestParam("productId") int productId,
            @RequestParam("purchaseId") String purchaseId,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        Inventory inventory = new Inventory();
        inventory.setQuantity(quantity);
        inventory.setCategory(category);

        if (photo != null) {
            inventory.setPhotoURL(localStorageService.save(photo));
        }

        Product product = new Product();
        product.setId(productId);
        inventory.setProduct(product);

        Purchase purchase = new Purchase();
        purchase.setId(purchaseId);
        inventory.setPurchase(purchase);

        service.save(inventory);
        return getList();
    }
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_UPDATE')")
    @PostMapping(path = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> updateInventory(
            @RequestParam("id") int id,
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category,
            @RequestParam("productId") int productId,
            @RequestParam("purchaseId") String purchaseId,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        Inventory inventory = service.getById(id);
        if (inventory == null) {
            throw new RuntimeException("No se encontró el inventario con ID: " + id);
        }

        inventory.setQuantity(quantity);
        inventory.setCategory(category);

        if (photo != null) {
            inventory.setPhotoURL(localStorageService.save(photo));
        }

        Product product = new Product();
        product.setId(productId);
        inventory.setProduct(product);

        Purchase purchase = new Purchase();
        purchase.setId(purchaseId);
        inventory.setPurchase(purchase);

        service.save(inventory);
        return getList();
    }
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_DELETE')")
    @DeleteMapping("/delete")
    public Map<String, Object> deleteInventory(@RequestParam int id) {
        service.delete(id);
        return getList();
    }
    
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_VIEW')")
    @GetMapping("/getById")
    public Inventory getInventoryById(@RequestParam int id) {
        return service.getById(id);
    }
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_VIEW')")
    @GetMapping("/expiration")
    public Map<String, Object> getByExpirationDate(@RequestParam("date") String date) {
        LocalDate expirationDate = LocalDate.parse(date);
        return Collections.singletonMap("inventory", service.findByExpirationDate(expirationDate));
    }
}
