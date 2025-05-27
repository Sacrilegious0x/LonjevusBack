
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import java.util.LinkedList;


public interface PermissionDAO extends CRUD<Permission> {
    
    public LinkedList<Permission> getByRole(int roleId);
    public boolean updatePermission(int roleId, int moduleId, Permission p);
    
}
