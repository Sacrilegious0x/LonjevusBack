
package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISupplierRepository extends JpaRepository<Supplier,Integer> {
    
}
