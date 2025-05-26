/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IPermissionService {
    
     void save(Permission permission);
    List<Permission> getAllPermissions();
    void delete(int permissionId);
    Permission getById(int permissionId);
    
}
