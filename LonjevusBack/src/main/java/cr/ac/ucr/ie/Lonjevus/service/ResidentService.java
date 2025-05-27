package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.repository.ResidentDAOImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class ResidentService {
    
    public static ResidentDAOImplement data = new ResidentDAOImplement();
    
    public static LinkedList<Resident> getAll(){
        return data.getAll();
    }
    
    public static void add(Resident r){
        data.add(r);
    }
    
    public static void delete(int id){
        data.deleteById(id);
    }
    
    public static Resident findById(int id){
        return data.findById(id);
    }
    
    public static LinkedList<Resident> findByNameorIdentification(String value){
        return data.getByNameorId(value);
    }
    
    public static void update(Resident resident){
        data.update(resident);
    }
}
