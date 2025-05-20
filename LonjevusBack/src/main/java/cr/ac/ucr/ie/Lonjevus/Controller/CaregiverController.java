/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Caregiver;
import cr.ac.ucr.ie.Lonjevus.domain.Schedule;
import cr.ac.ucr.ie.Lonjevus.service.CaregiverService;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;
import cr.ac.ucr.ie.Lonjevus.service.ScheduleService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/caregiver")
@CrossOrigin(origins = "http://localhost:5173")
public class CaregiverController {

    private CaregiverService serviceC = new CaregiverService();
    private ScheduleService servicesS = new ScheduleService();
    private LocalStorageService localStorageService = new LocalStorageService();

    @GetMapping("/listCaregiver")
    public Map getAll() {
        List<Caregiver> caregivers = serviceC.getAll();
        for (Caregiver c : caregivers) {
            Schedule shd = servicesS.getScheduleById(c.getScheduleId());
            c.setSchedule(shd);
        }

        return Collections.singletonMap("data", caregivers);
    }

    @PostMapping(value = "/addCaregiver", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addCaregiver(@RequestPart("caregiverData") Caregiver c,
            @RequestPart(value = "photo") MultipartFile photoFile) {
        try {
            Schedule shd = c.getSchedule();
            int idSchedule = servicesS.addAndReturn(shd);
            c.setScheduleId(idSchedule);
            
            if (photoFile != null && !photoFile.isEmpty()) {               
                String photoPath = localStorageService.saveCaregiverPhoto(photoFile);               
                c.setPhotoUrl(photoPath);
            }

            serviceC.addCaregiver(c);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear trabajador");
        }
        return null;
    }

    @PostMapping(value = "/updateCaregiver/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateCaregiver(@RequestPart("caregiverData") Caregiver c,
            @RequestPart(value = "photo") MultipartFile photoFile) {
        try{
            Schedule shd = c.getSchedule();
            servicesS.updateSchedule(shd);
            if (photoFile != null && !photoFile.isEmpty()) {               
                String photoPath = localStorageService.saveCaregiverPhoto(photoFile);
                c.setPhotoUrl(photoPath);
            }
            
            c.setScheduleId(shd.getId());
            System.out.println("id del horario por parametro " + c.getScheduleId());
            serviceC.updateCaregiver(c);
             return ResponseEntity.ok("Trabajador actualizado exitosamente");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear trabajador");
        }
        
         
    }

    @DeleteMapping("/deleteCaregiver/{id}")
    public ResponseEntity<String> deleteCaregiver(@PathVariable int id) {
         try {
            serviceC.deleteCaregiver(id);  
            return ResponseEntity.ok("Trabajador eliminado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el administrador");
        }
    }

    @GetMapping("getcaregiverById/{id}")
    public Caregiver getById(@PathVariable int id) {
        Caregiver c = serviceC.getCaregiverById(id);
        Schedule shd = servicesS.getScheduleById(c.getScheduleId());
        c.setSchedule(shd);       
        return c;
    }
}
