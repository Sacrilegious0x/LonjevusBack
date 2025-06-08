package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import cr.ac.ucr.ie.Lonjevus.service.IResidentContactService;
import cr.ac.ucr.ie.Lonjevus.service.IResidentService;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;
import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController

public class ResidentController {
    
    @Autowired
    private IResidentService service;
    
    @Autowired
    private IResidentContactService contactService;

    private static LocalStorageService localS = new LocalStorageService();
    
    @GetMapping("/residents")
    public List<Resident> getResidents() {
        return service.getList();
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
        resident.setIsActive(true);

        if (photo != null && !photo.isEmpty()) {
            String photoPath = localS.saveResidentPhoto(photo);
            resident.setPhoto(photoPath);

        } else {
            resident.setPhoto("foto.jpg");
        }

        service.save(resident);
        return "Residente listado";
    }

    @DeleteMapping("/deleteResident")
    @ResponseBody
    public String deleteResident(@RequestParam int id) {
        service.delete(id);
        return "Residente eliminado";
    }

    @GetMapping("/findResident")
    public Resident seachById(@RequestParam int id) {
        return service.getById(id);
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
        
        service.update(id, resident);
        return "Residente Actualizado";
    }

    @GetMapping("/getContacts")
    public List<ResidentContact> getContacts(@RequestParam int id) {
        return contactService.getList(id);
    }

    @PostMapping("/addContact")
    public String addContact(@RequestBody ResidentContact r) {
        contactService.save(r);
        return "Contacto agregado";
    }

    @DeleteMapping("/deleteContact")
    public String deleteContact(@RequestParam int id) {
        contactService.delete(id);
        return "Contacto eliminado";
    }

    @PostMapping("/updateContact")
    public String updateContact(@RequestBody ResidentContact r) {
        contactService.update(r.getId(), r);
        return "Contacto actualizado";
    }
}
