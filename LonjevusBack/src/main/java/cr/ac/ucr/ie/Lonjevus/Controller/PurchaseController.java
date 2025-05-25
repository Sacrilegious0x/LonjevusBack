package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.service.PurchaseService;
import java.util.LinkedList;
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

    private final PurchaseService purchaseService = new PurchaseService();

    @GetMapping("/all")
    public LinkedList<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPurchase(@RequestBody Purchase purchase) {
        try {
            purchaseService.addPurchase(purchase);
            return ResponseEntity.ok().body("{\"message\": \"Compra registrada correctamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Error al registrar la compra\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseById(@PathVariable String id) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(id);
            return ResponseEntity.ok(purchase);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Compra no encontrada");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable String id, @RequestBody Purchase purchase) {
        try {
            purchaseService.updatePurchase(id, purchase);
            return ResponseEntity.ok("Compra actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
