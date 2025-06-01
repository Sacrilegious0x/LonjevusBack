/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

/**
 *
 * @author Usuario
 */
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import java.util.List;

public interface IPurchaseService {
    List<Purchase> getAll();
    Purchase findById(String id);
    void save(Purchase purchase);
    void update(String id, Purchase purchase);
    void delete(String id);
}
