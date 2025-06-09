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
public interface IBillingRepository extends JpaRepository<Billing, Integer> {

    List<Billing> findAllByIsActiveTrue();

    List<Billing> findAllByIsActiveFalse();

    List<Billing> findByDate(LocalDate date);

    List<Billing> findByPeriodContainingIgnoreCase(String period);

    List<Billing> findByResidentId(Integer residentId);

    List<Billing> findByResidentIdAndDate(Integer residentId, LocalDate date);
   
    List<Billing> findByResidentIdAndIsActiveTrue(Integer residentId);
    
    List<Billing> findByIsActiveTrueAndResidentIsActiveFalse();

    List<Billing> findByResidentIsActiveFalse();


}
