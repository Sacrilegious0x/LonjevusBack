
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.PermissionDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class PermissionDAOImplement implements PermissionDAO {

    @Override
    public LinkedList<Permission> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public LinkedList<Permission> getByRole(int roleId) {
        String query = "CALL sp_get_permissions_by_role(?)";
        LinkedList<Permission> list = new LinkedList<>();

        try (Connection cn = ConnectionDB.getConnection();
             CallableStatement cs = cn.prepareCall(query)) {

            cs.setInt(1, roleId);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Permission pm = new Permission();
                    pm.setModuleCode(rs.getString("module_code"));
                    pm.setModuleName(rs.getString("module_name"));
                    pm.setCanView(rs.getBoolean("canView"));
                    pm.setCanCreate(rs.getBoolean("canCreate"));
                    pm.setCanUpdate(rs.getBoolean("canUpdate"));
                    pm.setCanDelete(rs.getBoolean("canDelete"));
                    list.add(pm);
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Error in getByRole: " + e.getMessage());
        }
        return list;
    }
    
    
    @Override
    public boolean updatePermission(int roleId, int moduleId, Permission p) {
        String query = "CALL sp_update_permission(?,?,?,?,?,?)";
        try (Connection cn = ConnectionDB.getConnection();
             CallableStatement cs = cn.prepareCall(query)) {

            cs.setInt(1, roleId);
            cs.setInt(2, moduleId);
            cs.setBoolean(3, p.getCanView());
            cs.setBoolean(4, p.getCanCreate());
            cs.setBoolean(5, p.getCanUpdate());
            cs.setBoolean(6, p.getCanDelete());

            int updatedRows = cs.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException e) {
            System.err.println("SQL Error in updatePermission: " + e.getMessage());
            return false;
        }
    }


    @Override
    public void add(Permission t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Permission t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Permission findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
