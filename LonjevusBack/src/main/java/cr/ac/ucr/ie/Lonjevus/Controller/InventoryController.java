package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.service.InventoryService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    private final InventoryService inventoryS;

    public InventoryController() {
        this.inventoryS = new InventoryService();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> list = inventoryS.getAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable int id) {
        inventoryS.deleteInventoryById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/expiration")
    public ResponseEntity<List<Inventory>> getByExpirationDate(@RequestParam("date") String date) {
        LocalDate expirationDate = LocalDate.parse(date);
        List<Inventory> result = inventoryS.findByExpirationDate(expirationDate);
        return ResponseEntity.ok(result);
    }
}
