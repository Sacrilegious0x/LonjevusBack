/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Activity;
import cr.ac.ucr.ie.Lonjevus.service.IActivityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ActivityController {

    @Autowired
    private IActivityService service;

    @GetMapping("/activities")
    public List<Activity> getActivities() {
        return service.getAll();
    }

    @PostMapping("/addActivity")
    @ResponseBody
    public String addActivity(@RequestBody Activity a) {
        a.setIsActive(true);
        service.save(a);
        return "Actividad agregada";
    }

    @DeleteMapping("/deleteActivity")
    public String deleteActivity(@RequestParam Integer id) {
        service.delete(id);
        return "actividad eliminada";
    }

    @PostMapping("/updateActivity")
    public String updateActivity(@RequestParam int id, @RequestBody Activity activity) {
        service.update(id, activity);
        return ("Actividad actualizada .");
    }

    @PostMapping("/addResidentToActivity")
    public String addResidentToActivity(@RequestParam int activityId, @RequestParam int residentId) {

        service.addResidentToActivity(residentId, activityId);
        return ("Residente agregado a la actividad");
    }
}
