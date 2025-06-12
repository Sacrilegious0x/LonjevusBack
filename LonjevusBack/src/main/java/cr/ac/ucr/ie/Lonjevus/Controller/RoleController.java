/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import cr.ac.ucr.ie.Lonjevus.domain.Role;
import cr.ac.ucr.ie.Lonjevus.service.IPermissionService;
import cr.ac.ucr.ie.Lonjevus.service.IRoleService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IPermissionService permService;
    
    @RequestMapping("/list")
    public Map getAllPermissions() {
        return Collections.singletonMap("roles", roleService.getAllRoles());
    }
    
    @PostMapping("/save")
    public Map<String,Object> createRole(@RequestBody Role role ){
        Role saveRoleToId = roleService.save(role);
        saveAllPermission(saveRoleToId.getId());
        return getAllPermissions();
    }
    
    public void saveAllPermission(int roleId){
        
      String [] modules = {"Inventario","Proveedor","Producto","Residente"
      ,"Cuidador","Habitación","Horario","Tarea"};
      
        for (String module : modules) {
            
            permService.save(new Permission(roleId,module,false,false,false,false));
            
        }
        
        
    }
    
    @DeleteMapping("/delete")
    public void deleteRole(@RequestParam int id){
        roleService.delete(id);
    }
    
}
