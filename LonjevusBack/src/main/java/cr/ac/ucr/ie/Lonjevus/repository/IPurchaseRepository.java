package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IPurchaseRepository extends JpaRepository<Purchase, String> {

    // Obtener todas las compras
    @Query(value = "CALL get_all_purchases()", nativeQuery = true)
    List<Object[]> getAllPurchasesRaw();

    // Obtener compra con productos
    @Query(value = "CALL get_purchase_with_details_by_id(:id)", nativeQuery = true)
    List<Object[]> getPurchaseDetailsById(@Param("id") String id);

   

    // Insertar productos de la compra
    @Modifying
    @Transactional
    @Query(value = "CALL insert_purchase_product(:purchaseId, :productId, :quantity, :expirationDate)", nativeQuery = true)
    void insertPurchaseProduct(
            @Param("purchaseId") String purchaseId,
            @Param("productId") int productId,
            @Param("quantity") int quantity,
            @Param("expirationDate") Date expirationDate
    );

    // Actualizar compra
    @Modifying
    @Transactional
    @Query(value = "CALL update_purchase(:id, :date, :amount)", nativeQuery = true)
    void updatePurchase(
            @Param("id") String id,
            @Param("date") Date date,
            @Param("amount") BigDecimal amount
    );

    // Eliminar productos anteriores
    @Modifying
    @Transactional
    @Query(value = "CALL delete_purchase_products(:purchaseId)", nativeQuery = true)
    void deletePurchaseProducts(@Param("purchaseId") String id);

    // Eliminado lógico de compra
    @Modifying
    @Transactional
    @Query(value = "CALL delete_purchase_by_id(:id)", nativeQuery = true)
    void deletePurchaseById(@Param("id") String id);
}
