
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
        
        try{
            Connection cn = ConnectionDB.getConnection();
            
            CallableStatement stmt = cn.prepareCall(query);
          
            ResultSet rs =stmt.executeQuery();
            
            Supplier supplier;
            
            while(rs.next()){
                
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
            
            
            }catch(SQLException e){
            System.out.println("Error de SQL" + e.getMessage());
            }
        return supp_list;
    }

    @Override
    public void add(Supplier t) {
        throw new UnsupportedOperationException(""); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Supplier t) {
        throw new UnsupportedOperationException(""); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer x) {
        throw new UnsupportedOperationException(""); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Supplier findById(Integer y) {
        throw new UnsupportedOperationException(""); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
