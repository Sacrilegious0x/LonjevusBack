/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Activity;
import cr.ac.ucr.ie.Lonjevus.repository.IActivityRepository;
import cr.ac.ucr.ie.Lonjevus.service.IActivityService;
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
public class ActivityServiceJPA implements IActivityService {
    
    @Autowired
    private IActivityRepository activityRepository;

    @Override
    public void save(Activity a) {
        activityRepository.save(a);
    }

    @Override
    public List<Activity> getAll() {
        return new LinkedList<>(activityRepository.findAll());
    }

    @Override
    public void delete(int id) {
        activityRepository.deleteById(id);
    }

    @Override
    public void update(int id, Activity a) {
        Optional<Activity> optActivity = activityRepository.findById(id);
        if(optActivity.isPresent()){
            Activity activity = optActivity.get();
            
            activity.setName(a.getName());
            activity.setDescription(a.getDescription());
            activity.setType(a.getType());
            activity.setDate(a.getDate());   
            activity.setStartTime(a.getStartTime());
            activity.setEndTime(a.getEndTime());
            activity.setLocation(a.getLocation());
            activity.setStatus(a.getStatus());
            
            activityRepository.save(activity);
        }
    }

    @Override
    public Activity getById(int id) {
       return activityRepository.findById(id).orElse(null);
    }
    
    
}
