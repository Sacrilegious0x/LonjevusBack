/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;


import cr.ac.ucr.ie.Lonjevus.domain.Caregiver;
import cr.ac.ucr.ie.Lonjevus.domain.Schedule;
import cr.ac.ucr.ie.Lonjevus.service.CaregiverService;
import cr.ac.ucr.ie.Lonjevus.service.ScheduleService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/caregiver")
public class CaregiverController {
    private  CaregiverService serviceC = new CaregiverService();
    private ScheduleService servicesS = new ScheduleService();
    @GetMapping("/listCaregiver")
    public Map getAll() {
        List<Caregiver> caregivers = serviceC.getAll();
        for(Caregiver c: caregivers){
            Schedule shd = servicesS.getScheduleById(c.getScheduleId());
            c.setSchedule(shd);
        }
       
        return Collections.singletonMap("data", caregivers);
    }
    
    @PostMapping("/addCaregiver")
    public Map addCaregiver(@RequestBody Caregiver c){
        Schedule shd = c.getSchedule();
        int idSchedule = servicesS.addAndReturn(shd);
        c.setScheduleId(idSchedule);
        serviceC.addCaregiver(c);
        return getAll();
    }
    @PostMapping("/updateCaregiver")
    public Map updateCaregiver(@RequestBody Caregiver c){
        Schedule shd = c.getSchedule();
        servicesS.updateSchedule(shd);
        c.setScheduleId(shd.getId());
        serviceC.updateCaregiver(c);
        return getAll();
    }
    @DeleteMapping("/deleteCaregiver/{id}")
    public Map deleteCaregiver(@PathVariable int id){
        serviceC.deleteCaregiver(id);
        return getAll();
    }
    
    @GetMapping("getcaregiverById/{id}")
    public Caregiver getById(@PathVariable int id){
        Caregiver c = serviceC.getCaregiverById(id);
        Schedule shd = servicesS.getScheduleById(c.getScheduleId());
        c.setSchedule(shd);
        return c;
    }
}
