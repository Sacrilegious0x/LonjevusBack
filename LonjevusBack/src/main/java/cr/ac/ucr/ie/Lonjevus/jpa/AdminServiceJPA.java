/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import cr.ac.ucr.ie.Lonjevus.repository.IAdminRepository;
import cr.ac.ucr.ie.Lonjevus.service.IAdminService;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class AdminServiceJPA implements IAdminService{
    
    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public void save(Admin a) {
        adminRepository.save(a);
    }

    @Override
    public LinkedList<Admin> getAll() {
        
       return new LinkedList<>(adminRepository.findAll());
    }

    @Override
    public void delete(int adminId) {
        adminRepository.deleteById(adminId);
    }

    @Override
    public void update(int adminId, Admin a) {
      Optional<Admin> optAdmin = adminRepository.findById(adminId);
      if(optAdmin.isPresent()){
          Admin admin = optAdmin.get();
          
          admin.setIdentification(a.getIdentification());
          admin.setName(a.getName());
          admin.setSalary(a.getSalary());
          admin.setEmail(a.getEmail());
          admin.setOficeContact(a.getOfficeContact());
          admin.setPhotoUrl(a.getPhotoUrl());
          admin.setSchedule(a.getSchedule());
      }
    }

    @Override
    public Admin getById(int adminId) {
       return adminRepository.findById(adminId).orElse(null);
    }
    
}
