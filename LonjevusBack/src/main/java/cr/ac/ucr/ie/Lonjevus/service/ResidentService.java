package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.ResidentDAOImplement;
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
}
