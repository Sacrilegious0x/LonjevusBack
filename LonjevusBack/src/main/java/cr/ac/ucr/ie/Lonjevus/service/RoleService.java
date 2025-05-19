/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.dao.PermissionDAO;
import cr.ac.ucr.ie.Lonjevus.dao.RoleDAO;
import cr.ac.ucr.ie.Lonjevus.daoImplements.PermissionDAOImplement;
import cr.ac.ucr.ie.Lonjevus.daoImplements.RoleDAOImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import cr.ac.ucr.ie.Lonjevus.domain.Role;
import java.util.List;


public class RoleService {
    
    
    private static final RoleDAO roleDao = new RoleDAOImplement();
    private static final PermissionDAO permDao = new PermissionDAOImplement();

     
    public static List<Role> listRoles() {
        return roleDao.getAll();
    }
     
    public List<Permission> getPermissions(int roleId) {
        return permDao.getByRole(roleId);
    }

    /**
     * Guarda (actualiza) el conjunto de permisos de un rol.Cada Permission ya tiene su moduleId,no  se ocupa
     otra DAO para buscar codigos o ids de módulos.
     * @param roleId
     * @param perms
     */
    public static void savePermissions(int roleId, List<Permission> perms) {
        for (Permission p : perms) {
            permDao.updatePermission(roleId, p.getModuleId(), p);
            
        }
    }
}
    

