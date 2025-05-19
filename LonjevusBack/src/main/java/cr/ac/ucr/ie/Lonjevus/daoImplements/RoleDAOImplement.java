
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.RoleDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class RoleDAOImplement implements RoleDAO {
    
    
    @Override
    public LinkedList<Role> getAll() {
        String query = "CALL sp_get_roles()";
        LinkedList<Role> list = new LinkedList<>();

        try (Connection cn = ConnectionDB.getConnection();
             CallableStatement cs = cn.prepareCall(query);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                r.setIsActive(rs.getBoolean("is_active"));
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error in getAll Roles: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Role t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Role findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Role t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    
    

