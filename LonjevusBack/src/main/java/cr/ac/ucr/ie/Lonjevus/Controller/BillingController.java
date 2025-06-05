package cr.ac.ucr.ie.Lonjevus.controller;

import cr.ac.ucr.ie.Lonjevus.domain.Billing;
import cr.ac.ucr.ie.Lonjevus.service.IBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*") 
public class BillingController {

    @Autowired
    private IBillingService billingService;

    // Guardar nueva factura
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Billing billing) {
        try {
            billingService.save(billing);
            return ResponseEntity.ok("Factura guardada exitosamente con consecutivo: " + billing.getConsecutive());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar factura: " + e.getMessage());
        }
    }

    // Obtener todas las facturas activas
    @GetMapping("/active")
    public ResponseEntity<List<Billing>> getAllActive() {
        return ResponseEntity.ok(billingService.getAllActive());
    }

    // Obtener factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Billing billing = billingService.findById(id);
            return ResponseEntity.ok(billing);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Factura no encontrada: " + e.getMessage());
        }
    }

    // Actualizar factura existente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Billing billing) {
        try {
            billingService.update(id, billing);
            return ResponseEntity.ok("Factura actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar factura: " + e.getMessage());
        }
    }

    // Eliminar (lógico) factura
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            billingService.delete(id);
            return ResponseEntity.ok("Factura eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar factura: " + e.getMessage());
        }
    }

    // Buscar por fecha
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Billing>> getByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(billingService.findByDate(localDate));
    }

    // Buscar por período
    @GetMapping("/period/{period}")
    public ResponseEntity<List<Billing>> getByPeriod(@PathVariable String period) {
        return ResponseEntity.ok(billingService.findByPeriod(period));
    }
}
