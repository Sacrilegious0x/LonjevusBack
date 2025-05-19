
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.SupplierDAOImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import java.util.LinkedList;



public class SupplierService {
    
    private static SupplierDAOImplement data = new SupplierDAOImplement();
    
    public static LinkedList<Supplier> getAllSupplier(){
        return data.getAll();
    }
    
    public static void addSupplier(Supplier supplier){
        data.add(supplier);
    }
    
    public static void deleteSupplierById(int id){
        data.deleteById(id);
    }
    
    public static Supplier getBySupplierId(int id){
        return data.findById(id);
    }
    
    public static void updateSupplier(Supplier supplier){
        data.update(supplier);
    }
    
}
