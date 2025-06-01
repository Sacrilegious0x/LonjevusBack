/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;
import cr.ac.ucr.ie.Lonjevus.service.ResidentContactService;
import cr.ac.ucr.ie.Lonjevus.service.ResidentService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
//@Controller
public class ResidentController {
    private static LocalStorageService localS = new LocalStorageService();
    @GetMapping("/residents")
    public List<Resident> getResidents() {
        return ResidentService.getAll();
    }

    @PostMapping("/addResident")
    @ResponseBody
    public String addResident(@RequestParam String identification, @RequestParam String name,
            @RequestParam String birthdate, @RequestParam String healthStatus,
            @RequestParam int numberRoom, @RequestParam(required = false) MultipartFile photo) {

        Resident resident = new Resident();
        resident.setIdentification(identification);
        resident.setName(name);
        resident.setBirthdate(LocalDate.parse(birthdate));
        resident.setHealthStatus(healthStatus);
        resident.setNumberRoom(numberRoom);

        if (photo != null && !photo.isEmpty()) {
            String photoPath = localS.saveResidentPhoto(photo);
            resident.setPhoto(photoPath);
        } else {
            resident.setPhoto("foto.jpg");
        }

        ResidentService.add(resident);
        return "Residente listado";
    }

    @DeleteMapping("/deleteResident")
    @ResponseBody
    public String deleteResident(@RequestParam int id) {
        ResidentService.delete(id);
        return "Residente eliminado";
    }

    @GetMapping("/findResident")
    public Resident seachById(@RequestParam int id) {
        return ResidentService.findById(id);
    }

    @GetMapping("/findResidentByNameorIdentification")
    public LinkedList<Resident> getResidentByNameorIdentification(@RequestParam String value) {
        return ResidentService.findByNameorIdentification(value);
    }

    @PostMapping("/updateResident")
    public String updateResident(@RequestParam int id, @RequestParam String identification, @RequestParam String name,
            @RequestParam String birthdate, @RequestParam String healthStatus,
            @RequestParam int numberRoom, @RequestParam(required = false) MultipartFile photo) {
        
        Resident resident = new Resident();
        resident.setId(id);
        resident.setIdentification(identification);
        resident.setName(name);
        resident.setBirthdate(LocalDate.parse(birthdate));
        resident.setHealthStatus(healthStatus);
        resident.setNumberRoom(numberRoom);

        if (photo != null && !photo.isEmpty()) {
            String photoPath = localS.saveResidentPhoto(photo);
            resident.setPhoto(photoPath);
        }else {
            resident.setPhoto("foto.jpg");
        }
        
        ResidentService.update(resident);
        return "Residente Actualizado";
    }

    @GetMapping("/getContacts")
    public List<ResidentContact> getContacts(@RequestParam int id) {
        return ResidentContactService.getAll(id);
    }

    @PostMapping("/addContact")
    public String addContact(@RequestBody ResidentContact r) {
        ResidentContactService.addContact(r);
        return "Contacto agregado";
    }

    @DeleteMapping("/deleteContact")
    public String deleteContact(@RequestParam int id) {
        ResidentContactService.deleteContact(id);
        return "Contacto eliminado";
    }

    @PostMapping("/updateContact")
    public String updateContact(@RequestBody ResidentContact r) {
        ResidentContactService.updateContact(r);
        return "Contacto actualizado";
    }
}
