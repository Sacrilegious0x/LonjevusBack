/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Role;
import cr.ac.ucr.ie.Lonjevus.service.IRoleService;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {
    
    @Autowired
    private IRoleService roleService;
    
    @RequestMapping("/list")
    public Map getAllPermissions() {
        return Collections.singletonMap("roles", roleService.getAllRoles());
    }
    
    @PostMapping("/save")
    public Map<String,Object> createRole(@RequestBody Role role ){
        roleService.save(role);
        return getAllPermissions();
    }
    
}
