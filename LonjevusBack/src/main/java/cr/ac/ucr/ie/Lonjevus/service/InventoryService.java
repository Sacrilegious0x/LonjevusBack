/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.InventoryDAOImplements;
import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
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
    
    public void update(Inventory inventory) {
    inventoryDAO.update(inventory);
}

    public void deleteById(Integer id) {
    inventoryDAO.deleteById(id);
}

    
}
