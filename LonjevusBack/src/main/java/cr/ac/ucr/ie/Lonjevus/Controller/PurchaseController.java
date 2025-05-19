/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.service.PurchaseService;
import java.util.LinkedList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public ResponseEntity<?> getPurchaseById(@PathVariable int id) {
    try {
        Purchase purchase = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(purchase);
    } catch (Exception e) {
        return ResponseEntity.status(404).body("Compra no encontrada");
    }
}

    
    @PutMapping("/update/{id}")
public ResponseEntity<?> updatePurchase(@PathVariable int id, @RequestBody Purchase purchase) {
    try {
        purchaseService.updatePurchase(id, purchase);
        return ResponseEntity.ok("Compra actualizada correctamente");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error al actualizar");
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
    purchaseService.deletePurchase(id);
    return ResponseEntity.noContent().build();
}



}
