/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Visit;
import cr.ac.ucr.ie.Lonjevus.repository.IVisitRepository;
import cr.ac.ucr.ie.Lonjevus.service.IVisitService;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */

@Service
public class VisitServiceJPA implements IVisitService{
    @Autowired
    private IVisitRepository visitRepository;
    @Override
    public void save(Visit v) {
         visitRepository.save(v);
    }

    @Override
    public LinkedList<Visit> getAll() {
         return new LinkedList<>(visitRepository.findAll());
    }

    @Override
    public void delete(int visitId) {
        visitRepository.deleteById(visitId);
    }

    @Override
    public void update(int visitId, Visit v) {
         Optional<Visit> optVisit = visitRepository.findById(visitId);
        if (optVisit.isPresent()) {
            Visit visit = optVisit.get();
            visit.setName(v.getName());
            visit.setVisitDate(v.getVisitDate());
            visit.setVisitHour(v.getVisitHour());
            visit.setPhoneNumber(v.getPhoneNumber());
            visit.setEmail(v.getEmail());
            visit.setRelationship(v.getRelationship());
            visitRepository.save(visit);
        }
    }

    @Override
    public Visit getById(int visitId) {
        return visitRepository.findById(visitId).orElse(null);
    }

  
}
