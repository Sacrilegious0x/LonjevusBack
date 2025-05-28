package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.PurchaseDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
//import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class PurchaseDAOImplements implements PurchaseDAO {

    @Override
    public LinkedList<Purchase> getAll() {
        LinkedList<Purchase> purchases = new LinkedList<>();
        String sql = "CALL get_all_purchases();";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String purchaseId = rs.getString("purchase_id");

                //Buscar si la compra ya fue agregada
                Purchase existingPurchase = null;
                for (Purchase p : purchases) {
                    if (p.getId().equals(purchaseId)) {
                        existingPurchase = p;
                        break;
                    }
                }

                if (existingPurchase == null) {
                    existingPurchase = new Purchase();
                    existingPurchase.setId(purchaseId);
                    existingPurchase.setDate(rs.getDate("purchase_date").toLocalDate());
                    existingPurchase.setAmount(rs.getBigDecimal("purchase_amount"));

                    //Admin admin = new Admin();
                    //admin.setName(rs.getString("admin_name"));
                    //existingPurchase.setAdmin(admin);
                    existingPurchase.setItems(new LinkedList<>());
                    purchases.add(existingPurchase);
                }

                // Agregar producto
                PurchaseProduct item = new PurchaseProduct();
                item.setIdProduct(rs.getInt("idProduct"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("product_price"));

                existingPurchase.getItems().add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchases;
    }

    @Override
    public void add(Purchase purchase) {
        if (purchase.getItems() == null || purchase.getItems().isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una compra sin productos.");
        }

        try (Connection cn = ConnectionDB.getConnection()) {
            cn.setAutoCommit(false);

            //INSERT A LA TABLA DE COMPRAS
            String sqlPurchase = "CALL insert_purchase(?, ?, ?)";
            String purchaseId = null;

            try (CallableStatement cs = cn.prepareCall(sqlPurchase)) {
                cs.setDate(1, java.sql.Date.valueOf(purchase.getDate()));
                cs.setBigDecimal(2, purchase.getAmount());
                cs.setInt(3, 1);
                //cs.setInt(3, purchase.getAdmin().getId());

                ResultSet rs = cs.executeQuery();
                if (rs.next()) {
                    purchaseId = rs.getString("new_purchase_id");
                }
            }
            //INSERT A LA TABLA DE LA RELACION N:M
            String sqlDetail = "CALL insert_purchase_product(?, ?, ?, ?)";
            for (PurchaseProduct item : purchase.getItems()) {
                try (CallableStatement csItem = cn.prepareCall(sqlDetail)) {
                    csItem.setString(1, purchaseId);
                    csItem.setInt(2, item.getIdProduct());
                    csItem.setInt(3, item.getQuantity());
                    csItem.setDate(4, java.sql.Date.valueOf(item.getExpirationDate()));
                    csItem.executeUpdate();
                }
            }

            //TAMBIEN SE VAN A AGREGAR LOS PRODUCTOS DE LA COMPRA AL INVENARIO
            InventoryDAOImplements inventoryDAO = new InventoryDAOImplements();
            for (PurchaseProduct item : purchase.getItems()) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    Inventory inv = new Inventory();
                    inv.setQuantity(1);
                    inv.setCategory("Default");
                    inv.setPhotoURL("default.png");

                    Product product = new Product();
                    product.setId(item.getIdProduct());
                    product.setExpirationDate(item.getExpirationDate());
                    inv.setProduct(product);

                    Purchase pur = new Purchase();
                    pur.setId(purchaseId);
                    inv.setPurchase(pur);

                    inventoryDAO.addWithConnection(inv, cn); //Se estaban bloqueando las conexiones, la solucion es reutilizarla
                }
            }

            cn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Purchase purchase) {
        if (purchase.getItems() == null || purchase.getItems().isEmpty()) {
            throw new IllegalArgumentException("Debe tener al menos un producto.");
        }

        try (Connection cn = ConnectionDB.getConnection()) {
            cn.setAutoCommit(false);

            String updateSQL = "CALL update_purchase(?, ?, ?)";
            try (CallableStatement stmt = cn.prepareCall(updateSQL)) {
                stmt.setString(1, purchase.getId());
                stmt.setDate(2, java.sql.Date.valueOf(purchase.getDate()));
                stmt.setBigDecimal(3, purchase.getAmount());
                stmt.executeUpdate();
            }

            String deleteOld = "CALL delete_purchase_products(?)";
            try (CallableStatement stmt = cn.prepareCall(deleteOld)) {
                stmt.setString(1, purchase.getId());
                stmt.executeUpdate();
            }

            String insertProduct = "CALL insert_purchase_product(?, ?, ?, ?)";
            for (PurchaseProduct item : purchase.getItems()) {
                try (CallableStatement stmt = cn.prepareCall(insertProduct)) {
                    stmt.setString(1, purchase.getId());
                    stmt.setInt(2, item.getIdProduct());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setDate(4, java.sql.Date.valueOf(item.getExpirationDate()));
                    stmt.executeUpdate();
                }
            }

            cn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar la compra.");
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "CALL delete_purchase_by_id(?)";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar lógicamente la compra con ID " + id);
        }
    }

    @Override
    public Purchase findById(String id) {
        Purchase purchase = null;
        String sql = "CALL get_purchase_with_details_by_id(?);";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (purchase == null) {
                    purchase = new Purchase();
                    purchase.setId(rs.getString("purchase_id"));
                    purchase.setDate(rs.getDate("purchase_date").toLocalDate());
                    purchase.setAmount(rs.getBigDecimal("purchase_amount"));

                    //Admin admin = new Admin();
                    //admin.setId(rs.getInt("admin_id"));
                    //admin.setName(rs.getString("admin_name"));
                    //purchase.setAdmin(admin);
                    purchase.setItems(new LinkedList<>());
                }

                PurchaseProduct item = new PurchaseProduct();
                item.setIdProduct(rs.getInt("idProduct"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getBigDecimal("product_price"));
                item.setQuantity(rs.getInt("quantity"));
                purchase.getItems().add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchase;
    }

    @Override
    public Purchase findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}