/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import cr.ac.ucr.ie.Lonjevus.service.IPermissionService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permissions")
@CrossOrigin(origins = "http://localhost:5173")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    // Obtener todos los permisos
    @RequestMapping("/list")
    public Map getAllPermissions() {
        return Collections.singletonMap("permissions", permissionService.getAllPermissions());
    }

    // Obtener permiso por ID
    @GetMapping("/getById")
    public ResponseEntity<Permission> getPermissionById(@PathVariable("id") int id) {
        Permission permission = permissionService.getById(id);
        if (permission != null) {
            return ResponseEntity.ok(permission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear o actualizar un permiso
    @PostMapping("/save")
    public ResponseEntity<Permission> createOrUpdatePermission(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResponseEntity.ok(permission);
    }

    // Eliminar un permiso
    @DeleteMapping("/delete")
    public Map deletePermission(@RequestParam int id) {
        permissionService.delete(id);
        return getAllPermissions();
    }
}
