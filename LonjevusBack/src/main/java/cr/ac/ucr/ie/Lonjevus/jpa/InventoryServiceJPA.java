/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.repository.IInventoryRepository;
import cr.ac.ucr.ie.Lonjevus.service.IInventoryService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class InventoryServiceJPA implements IInventoryService {

    @Autowired
    private IInventoryRepository repo;

    @Override
    public void save(Inventory inventory) {
        repo.save(inventory);
    }

    @Override
    public List<Inventory> getAll() {
    List<Object[]> rows = repo.getAllInventoryRaw();
    List<Inventory> list = new LinkedList<>();

    for (Object[] row : rows) {
        Inventory inventory = new Inventory();
        inventory.setId((Integer) row[0]);
        inventory.setQuantity((Integer) row[1]);
        inventory.setCategory((String) row[2]);
        inventory.setPhotoURL((String) row[3]);
        inventory.setActivo((Boolean) row[4]);
       
        Product product = new Product();
        product.setId((Integer) row[5]);
        product.setName((String) row[6]);
        product.setExpirationDate(row[7] != null ? ((Date) row[7]).toLocalDate() : null);

        Supplier supplier = new Supplier();
        supplier.setId((Integer) row[8]);
        supplier.setName((String) row[9]);
        product.setSupplier(supplier);

        Purchase purchase = new Purchase();
        purchase.setId((String) row[10]);
        
        

        inventory.setProduct(product);
        inventory.setPurchase(purchase);

        list.add(inventory);
    }

    return list;
}


    @Override
    public void delete(int inventoryId) {
        repo.deleteLogicallyById(inventoryId);
    }

    @Override
    public Inventory getById(int inventoryId) {
        return repo.findById(inventoryId).orElse(null);
    }

    @Override
    public List<Inventory> findByExpirationDate(LocalDate expirationDate) {
        return repo.findByProduct_ExpirationDate(expirationDate);
    }

    public void insertInventory(Inventory inventory) {
        repo.insertInventory(
                inventory.getProduct().getId(),
                inventory.getCategory(),
                inventory.getPhotoURL(),
                inventory.getQuantity(),
                inventory.getPurchase().getId()
        );
    }
}
