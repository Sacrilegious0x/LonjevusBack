
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Longevus.repository.ISupplierRepository;
import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SupplierService {

    
    @Autowired
    private static ISupplierRepository repo;
    
    public static LinkedList<Supplier> getAllSupplier(){
         return (LinkedList<Supplier>) repo.findAll();
    }
    
    public static void addSupplier(Supplier supplier){
        repo.save(supplier);
    }
    
    public static void deleteSupplierById(int id){
        repo.deleteById(id);
    }
    
    public static Supplier getBySupplierId(int id){
        return repo.getById(id);
    }
    
    public static void updateSupplier(Supplier supplier){
        repo.save(supplier);
    }
    
}
