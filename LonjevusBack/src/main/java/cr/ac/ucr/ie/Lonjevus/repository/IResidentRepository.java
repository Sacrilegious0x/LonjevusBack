/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public interface IResidentRepository extends JpaRepository<Resident, Integer>{
    
}
