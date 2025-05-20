/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ProductDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.domain.Unit;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class ProductDAOImplements implements ProductDAO {

    @Override
    public LinkedList<Product> getAll() {
        LinkedList<Product> list = new LinkedList<>();
        String sql = "CALL get_all_products();";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setCategory(rs.getString("category"));
                p.setExpirationDate(rs.getDate("expirationDate").toLocalDate());
                p.setPhotoURL(rs.getString("photoURL"));


                Unit unit = new Unit();
                unit.setId(rs.getInt("unitId"));
                unit.setUnitType(rs.getString("unit_type"));
                p.setUnit(unit);


                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("supplier_name"));
                supplier.setPhoneNumber(rs.getString("phoneNumber"));
                supplier.setEmail(rs.getString("email"));
                supplier.setAddress(rs.getString("address"));
                supplier.setPhoto(rs.getString("supplier_photo"));
                p.setSupplier(supplier);

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void add(Product p) {
        String sql = "CALL insert_product(?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setString(1, p.getName());
            stmt.setBigDecimal(2, p.getPrice());
            stmt.setDate(3, java.sql.Date.valueOf(p.getExpirationDate()));
            stmt.setString(4, p.getCategory());
            stmt.setString(5, p.getPhotoURL());
            stmt.setInt(6, p.getUnit().getId());
            stmt.setInt(7, p.getSupplier().getId());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product p) {
        String sql = "CALL update_product(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setBigDecimal(3, p.getPrice());
            stmt.setDate(4, java.sql.Date.valueOf(p.getExpirationDate()));
            stmt.setString(5, p.getCategory());
            stmt.setString(6, p.getPhotoURL());
            stmt.setInt(7, p.getUnit().getId());
            stmt.setInt(8, p.getSupplier().getId());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "CALL delete_product(?)";
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(Integer id) {
        Product product = null;
        String sql = "CALL get_product_by_id(?);";

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement stmt = cn.prepareCall(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setCategory(rs.getString("category"));
                product.setExpirationDate(rs.getDate("expirationDate").toLocalDate());
                product.setPhotoURL(rs.getString("photo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

}