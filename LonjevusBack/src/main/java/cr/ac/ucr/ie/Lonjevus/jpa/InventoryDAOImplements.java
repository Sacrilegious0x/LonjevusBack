/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;
import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.InventoryDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class InventoryDAOImplements implements InventoryDAO {

    @Override
    public LinkedList<Inventory> getAll() {
        LinkedList<Inventory> list = new LinkedList<>();
        String sql = "CALL get_all_inventory();";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String photoURL = rs.getString("photo_url");

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                Date sqlDate = rs.getDate("expiration_date");
                if (sqlDate != null) {
                    product.setExpirationDate(sqlDate.toLocalDate());
                } else {
                    product.setExpirationDate(null);
                }

                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("supplier_name"));
                product.setSupplier(supplier);

                Purchase purchase = new Purchase();
                purchase.setId(rs.getString("purchase_id"));

                Inventory inventory = new Inventory();
                inventory.setId(rs.getInt("inventory_id"));
                inventory.setQuantity(quantity);
                inventory.setCategory(category);
                inventory.setPhotoURL(photoURL);
                inventory.setProduct(product);
                inventory.setPurchase(purchase);

                list.add(inventory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void add(Inventory inventory) {
        String sql = "CALL insert_inventory(?, ?, ?, ?, ?)";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {

            stmt.setInt(1, inventory.getProduct().getId());
            stmt.setString(2, inventory.getCategory());
            stmt.setString(3, inventory.getPhotoURL());
            stmt.setInt(4, inventory.getQuantity());
            stmt.setString(5, inventory.getPurchase().getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
   public void update(Inventory inventory) {
}


    @Override
    public void deleteById(Integer id) {
        String sql = "CALL delete_inventory_logically_by_id(?)";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedList<Inventory> findByExpirationDate(LocalDate expirationDate) {
        LinkedList<Inventory> list = new LinkedList<>();
        String sql = "CALL get_inventory_by_expiration(?)";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {

            stmt.setDate(1, Date.valueOf(expirationDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String photoURL = rs.getString("photo_url");

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                Date sqlDate = rs.getDate("expiration_date");
                if (sqlDate != null) {
                    product.setExpirationDate(sqlDate.toLocalDate());
                }

                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("supplier_name"));
                product.setSupplier(supplier);

                Purchase purchase = new Purchase();
                purchase.setId(rs.getString("purchase_id"));

                Inventory inventory = new Inventory();
                inventory.setId(rs.getInt("inventory_id"));
                inventory.setQuantity(quantity);
                inventory.setCategory(category);
                inventory.setPhotoURL(photoURL);
                inventory.setProduct(product);
                inventory.setPurchase(purchase);

                list.add(inventory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addWithConnection(Inventory inventory, Connection cn) throws Exception {
        String sql = "CALL insert_inventory(?, ?, ?, ?, ?)";

        try (CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setInt(1, inventory.getProduct().getId());
            stmt.setString(2, inventory.getCategory());
            stmt.setString(3, inventory.getPhotoURL());
            stmt.setInt(4, inventory.getQuantity());
            stmt.setString(5, inventory.getPurchase().getId());
            stmt.executeUpdate();
        }
    }

}