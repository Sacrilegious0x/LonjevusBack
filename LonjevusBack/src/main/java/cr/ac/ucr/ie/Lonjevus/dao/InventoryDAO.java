/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.dao;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */
public interface InventoryDAO extends CRUD<Inventory>{
    LinkedList<Inventory> findByExpirationDate(LocalDate expirationDate);

}
