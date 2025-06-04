/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Billing;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
public interface IBillingRepository extends JpaRepository<Billing, String>{
    
    
    //Obtener todas las facturas que esten activas
    List<Billing> findAllByIsActiveTrue();
    
    //Agregar una factura
    
    
    
    //Editar una factura
    @Modifying
@Transactional
@Query(value = "CALL update_purchase(:id, :date, :amount)", nativeQuery = true)
void updatePurchase(
    @Param("id") String id,
    @Param("date") java.sql.Date date,
    @Param("amount") BigDecimal amount
);

    
    //Eliminar una factura, que al eliminar se ponga como factura cancelada, 
    //Su activo pasa a 0, para poder recuperar las facturas canceladas
    @Procedure(name = "delete_billing_logically")
    void deleteBillingLogically(@Param("billingId") Integer billingId);
    
    //Buscar por fecha de generacion
    List<Billing> findByDate(LocalDate date);
    
    // Buscar por período
    List<Billing> findByPeriodContainingIgnoreCase(String period);

    
}
