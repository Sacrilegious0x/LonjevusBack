package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.service.IPurchaseService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "http://localhost:5173")
public class PurchaseController {

    private final IPurchaseService purchaseService;

    @Autowired
    public PurchaseController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/all")
    public List<Purchase> getAllPurchases() {
        return purchaseService.getAll();
    }

    @GetMapping("/inactive")
    public List<Purchase> getInactivePurchases() {
        return purchaseService.getAllInactive();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPurchase(@RequestBody Purchase purchase) {
        try {
            purchaseService.save(purchase);
            return ResponseEntity.ok().body("{\"message\": \"Compra registrada correctamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Error al registrar la compra\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseById(@PathVariable String id) {
        try {
            Purchase purchase = purchaseService.findById(id);
            return ResponseEntity.ok(purchase);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Compra no encontrada");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable String id, @RequestBody Purchase purchase) {
        try {
            purchaseService.update(id, purchase);
            return ResponseEntity.ok("Compra actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable String id) {
        purchaseService.delete(id);
        return ResponseEntity.ok("Compra eliminada correctamente.");
    }

}
