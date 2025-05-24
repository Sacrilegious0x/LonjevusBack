/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.PurchaseDAOImplements;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */

import cr.ac.ucr.ie.Lonjevus.daoImplements.PurchaseDAOImplements;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public class PurchaseService {
    private static final PurchaseDAOImplements purchaseDAO = new PurchaseDAOImplements();

    public LinkedList<Purchase> getAllPurchases() {
        return purchaseDAO.getAll();
    }
    
    public void addPurchase(Purchase purchase) {
    purchaseDAO.add(purchase);
}
    
   public void updatePurchase(String id, Purchase purchase) {
    purchase.setId(String.valueOf(id));
    purchaseDAO.update(purchase);
}
   
   public Purchase getPurchaseById(String id) {
    return purchaseDAO.findById(id);
}
   
   public void deletePurchase(String id) {
    purchaseDAO.deleteById(id);
}


}
