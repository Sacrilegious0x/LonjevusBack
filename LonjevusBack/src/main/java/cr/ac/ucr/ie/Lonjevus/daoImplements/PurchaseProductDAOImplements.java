/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.PurchaseProductDAO;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class PurchaseProductDAOImplements implements PurchaseProductDAO {

    @Override
    public LinkedList<PurchaseProduct> getAll() {
        LinkedList<PurchaseProduct> list = new LinkedList<>();
        String sql = "CALL get_all_purchase_products()";

        try (Connection conn = ConnectionDB.getConnection(); CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PurchaseProduct pp = new PurchaseProduct();
                pp.setIdPurchase(rs.getInt("idPurchase"));
                pp.setIdProduct(rs.getInt("idProduct"));
                pp.setQuantity(rs.getInt("quantity"));
                list.add(pp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void add(PurchaseProduct pp) {
        String sql = "CALL insert_purchase_product(?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, pp.getIdPurchase());
            stmt.setInt(2, pp.getIdProduct());
            stmt.setInt(3, pp.getQuantity());

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PurchaseProduct t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PurchaseProduct findById(Integer y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
