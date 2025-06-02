package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query(value = "CALL get_inventory_by_expiration(:date)", nativeQuery = true)
    List<Object[]> findInventoryByExpirationDate(@Param("date") LocalDate date);

    @Query(value = "CALL get_all_inventory()", nativeQuery = true)
    List<Object[]> getAllInventoryRaw();

    @Modifying
    @Transactional
    @Query(value = "CALL insert_inventory(:productId, :category, :photoURL, :quantity, :purchaseId)", nativeQuery = true)
    void insertInventory(
            @Param("productId") int productId,
            @Param("category") String category,
            @Param("photoURL") String photoURL,
            @Param("quantity") int quantity,
            @Param("purchaseId") String purchaseId
    );

    @Modifying
    @Transactional
    @Query(value = "CALL delete_inventory_logically_by_id(:id)", nativeQuery = true)
    void deleteLogicallyById(@Param("id") int id);

    public List<Inventory> findByProduct_ExpirationDate(LocalDate expirationDate);
}
