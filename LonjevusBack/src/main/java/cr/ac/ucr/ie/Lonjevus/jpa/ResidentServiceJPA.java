/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.repository.IResidentRepository;
import cr.ac.ucr.ie.Lonjevus.service.IResidentService;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
@Service
public class ResidentServiceJPA implements IResidentService{
    
    @Autowired
    private IResidentRepository residentRepository;

    @Override
    public void save(Resident resident) {
        residentRepository.save(resident);
    }

    @Override
    public List<Resident> getList() {
        LinkedList<Resident> list = new LinkedList<>();
        
        for(Resident r: residentRepository.findAll()){
            if(r.isIsActive()){
                list.add(r);
            }
        }
        
        return list;
    }

    @Override
    public void delete(int id) {
        Resident resident = residentRepository.findById(id).orElse(null);
        
        resident.setIsActive(false);
        
        residentRepository.save(resident);
    }

    @Override
    public void update(int id, Resident r) {
        Optional<Resident> optResident = residentRepository.findById(id);
        if(optResident.isPresent()){
            Resident resident = optResident.get();
            
            resident.setIdentification(r.getIdentification());
            resident.setName(r.getName());
            resident.setBirthdate(r.getBirthdate());
            resident.setHealthStatus(r.getHealthStatus());
            resident.setNumberRoom(r.getNumberRoom());
            resident.setPhoto(r.getPhoto());   
            
            residentRepository.save(resident);
        }
    }

    @Override
    public Resident getById(int id) {
        return residentRepository.findById(id).orElse(null);
    }
    
}
