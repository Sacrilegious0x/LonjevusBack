/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.domain.Unit;
import cr.ac.ucr.ie.Lonjevus.repository.IProductRepository;
import cr.ac.ucr.ie.Lonjevus.service.IProductService;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplement implements IProductService {

    @Autowired
    private IProductRepository repo;

    @Override
    public LinkedList<Product> getAllProducts() {
        List<Object[]> rows = repo.getAllProductsRaw();
        LinkedList<Product> list = new LinkedList<>();

        for (Object[] row : rows) {
            Product p = new Product();
            p.setId((Integer) row[0]);
            p.setName((String) row[1]);
            p.setPrice((java.math.BigDecimal) row[2]);
            p.setCategory((String) row[3]);
            p.setExpirationDate(row[4] != null ? ((Date) row[4]).toLocalDate() : null);
            p.setPhotoURL((String) row[5]);

            Unit unit = new Unit();
            unit.setId((Integer) row[6]);
            unit.setUnitType((String) row[7]);
            p.setUnit(unit);

            Supplier supplier = new Supplier();
            supplier.setId((Integer) row[8]);
            supplier.setName((String) row[9]);
            supplier.setPhoneNumber((String) row[10]);
            supplier.setEmail((String) row[11]);
            supplier.setAddress((String) row[12]);
            supplier.setPhoto((String) row[13]);
            p.setSupplier(supplier);

            list.add(p);
        }
        return list;
    }

    @Override
    public void addProduct(Product p) {
        repo.insertProduct(
            p.getName(),
            p.getPrice(),
            Date.valueOf(p.getExpirationDate()),
            p.getCategory(),
            p.getPhotoURL(),
            p.getUnit().getId(),
            p.getSupplier().getId()
        );
    }

    @Override
    public void updateProduct(Product p) {
        repo.updateProduct(
            p.getId(),
            p.getName(),
            p.getPrice(),
            Date.valueOf(p.getExpirationDate()),
            p.getCategory(),
            p.getPhotoURL(),
            p.getUnit().getId(),
            p.getSupplier().getId()
        );
    }

    @Override
    public void deleteProduct(int id) {
        repo.deleteProduct(id);
    }

    @Override
    public Product findById(int id) {
        List<Object[]> rows = repo.getProductById(id);
        if (rows.isEmpty()) return null;

        Object[] row = rows.get(0);
        Product p = new Product();
        p.setId((Integer) row[0]);
        p.setName((String) row[1]);
        p.setPrice((java.math.BigDecimal) row[2]);
        p.setCategory((String) row[3]);
        p.setExpirationDate(((Date) row[4]).toLocalDate());
        p.setPhotoURL((String) row[5]);
        // puedes expandir si necesitas unit y supplier
        return p;
    }
}

