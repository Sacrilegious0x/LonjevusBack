/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.InventoryDAOImplements;
import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class InventoryService {
    
    private static InventoryDAOImplements inventoryDAO;
    
     public InventoryService() {
        this.inventoryDAO = new InventoryDAOImplements();
    }
    
    public static LinkedList<Inventory> getAll(){
        return inventoryDAO.getAll();
    }
    
   public void updateInventory(Inventory inventory) {
}


    public void deleteInventoryById(int id) {
    inventoryDAO.deleteById(id);
}

    public LinkedList<Inventory> findByExpirationDate(LocalDate expirationDate) {
        return inventoryDAO.findByExpirationDate(expirationDate);
    }
    

    
}
