/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.InventoryDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
//import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class InventoryDAOImplements implements InventoryDAO {

 
@Override
public LinkedList<Inventory> getAll() {
    LinkedList<Inventory> list = new LinkedList<>();
    String sql = "CALL get_all_inventory_full();";

    try (Connection cn = ConnectionDB.getConnection();
         CallableStatement stmt = cn.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Inventory inventory = new Inventory();
            inventory.setId(rs.getInt("inventory_id"));
            inventory.setQuantity(rs.getInt("total_quantity"));
            inventory.setCategory(rs.getString("category"));
            inventory.setPhotoURL(rs.getString("photo_url"));

            Product product = new Product();
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("product_name"));
            product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());

           // Supplier supplier = new Supplier();
            //supplier.setId(rs.getInt("supplier_id"));
            //supplier.setName(rs.getString("supplier_name"));

            //product.setSupplier(supplier);
            inventory.setProduct(product);

            Purchase purchase = new Purchase();
            purchase.setId(rs.getInt("purchase_id"));
            inventory.setPurchase(purchase);

            list.add(inventory);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    @Override
    public void add(Inventory t) {
    }

    @Override
    public void update(Inventory inventory) {
        String sql = "CALL update_inventory_by_id(?, ?, ?)";

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement stmt = cn.prepareCall(sql);
            stmt.setInt(1, inventory.getId());
            stmt.setInt(2, inventory.getQuantity());
            stmt.setString(3, inventory.getCategory());

            stmt.executeUpdate();

            stmt.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "CALL delete_inventory_logically_by_id(?)";

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement stmt = cn.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
