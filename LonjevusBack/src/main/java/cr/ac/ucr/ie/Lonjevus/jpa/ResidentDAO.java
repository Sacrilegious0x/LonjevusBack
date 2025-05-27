package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public interface ResidentDAO extends CRUD<Resident>{
    public LinkedList<Resident> getByNameorId(String value);
}
