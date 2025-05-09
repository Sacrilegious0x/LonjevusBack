package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.SupplierDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SupplierDAOImplement implements SupplierDAO {

    @Override
    public LinkedList<Supplier> getAll() {

        String query = "call sp_get_all_suppliers()";

        LinkedList<Supplier> supp_list = new LinkedList();

        try {
            Connection cn = ConnectionDB.getConnection();

            CallableStatement stmt = cn.prepareCall(query);

            ResultSet rs = stmt.executeQuery();

            Supplier supplier;

            while (rs.next()) {

                supplier = new Supplier();
                supplier.setId(rs.getInt(1));
                supplier.setName(rs.getString(2));
                supplier.setPhoneNumber(rs.getString(3));
                supplier.setEmail(rs.getString(4));
                supplier.setAddress(rs.getString(5));
                supplier.setPhoto(rs.getString(6));
                supplier.setIsActive(rs.getBoolean(7));
                supp_list.add(supplier);

            }

        } catch (SQLException e) {
            System.out.println("Error de SQL" + e.getMessage());
        }
        return supp_list;
    }

    @Override
    public void add(Supplier supplier) {
        String query = "call sp_add_supplier(?,?,?,?,?,?)";

        try {
            Connection cn = ConnectionDB.getConnection();

            CallableStatement stmt = cn.prepareCall(query);

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhoneNumber());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getAddress());
            stmt.setString(5, supplier.getPhoto());
            stmt.setBoolean(6, supplier.isIsActive());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL" + e.getMessage());
        }
    }

    @Override
    public void update(Supplier supplier) {
        String query = "call sp_update_supplier(?,?,?,?,?,?,?)";

        try{
            
            Connection cn = ConnectionDB.getConnection();       
            CallableStatement stmt = cn.prepareCall(query); 
            stmt.setInt(1, supplier.getId());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getPhoneNumber());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getPhoto());
            stmt.setBoolean(7, supplier.isIsActive());

            stmt.executeUpdate();
           

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
      
        String query = "call sp_delete_supplier(?)";
        
        try{
            
            Connection cn = ConnectionDB.getConnection();       
            CallableStatement stmt = cn.prepareCall(query);
            stmt.setInt(1, id); 
            
            stmt.executeUpdate();
            
        }catch(SQLException e){
            
            System.out.println("SQL Error: "+ e.getMessage());
            
        }
        
    }

    @Override
    public Supplier findById(Integer id) {
        
        Supplier supplier = null;
    String query = "call sp_get_supplier_by_id(?)";
    
    try (Connection cn = ConnectionDB.getConnection();
         CallableStatement stmt = cn.prepareCall(query)) {

        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
        
            supplier = new Supplier();
            supplier.setId(rs.getInt("id"));
            supplier.setName(rs.getString("name"));
            supplier.setPhoneNumber(rs.getString("phoneNumber"));
            supplier.setEmail(rs.getString("email"));
            supplier.setAddress(rs.getString("address"));
            supplier.setPhoto(rs.getString("photo"));
            supplier.setIsActive(rs.getBoolean("isActive"));
        }

    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    }

    return supplier;
        
    }

}
