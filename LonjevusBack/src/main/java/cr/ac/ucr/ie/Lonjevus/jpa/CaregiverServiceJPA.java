/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Caregiver;
import cr.ac.ucr.ie.Lonjevus.repository.ICaregiverRepository;
import cr.ac.ucr.ie.Lonjevus.service.ICaregiverService;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author User
 */
public class CaregiverServiceJPA implements ICaregiverService {

    @Autowired
    private ICaregiverRepository caregiverRepository;
    
    @Override
    public void save(Caregiver c) {
        caregiverRepository.save(c);
    }
    
    @Override
    public LinkedList<Caregiver> getAll() {
        return new LinkedList<>(caregiverRepository.findAll());
    }
    
    @Override
    public void delete(int caregiverId) {
        caregiverRepository.deleteById(caregiverId);
    }
    
    @Override
    public void update(int caregiverId, Caregiver c) {
        Optional<Caregiver> optCaregiver = caregiverRepository.findById(caregiverId);
        if (optCaregiver.isPresent()) {
            Caregiver caregiver = optCaregiver.get();
            caregiver.setIdentification(c.getIdentification());
            caregiver.setName(c.getName());
            caregiver.setSalary(c.getSalary());
            caregiver.setEmail(c.getEmail());
            caregiver.setShift(c.getShift());   
            caregiver.setPhotoUrl(c.getPhotoUrl());
            caregiver.setSchedule(c.getSchedule());
        }
    }
    
    @Override
    public Caregiver getById(int caregiverId) {
        return caregiverRepository.findById(caregiverId).orElse(null);
    }
    
}
