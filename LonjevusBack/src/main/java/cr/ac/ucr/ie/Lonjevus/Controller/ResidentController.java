/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import cr.ac.ucr.ie.Lonjevus.service.ResidentContactService;
import cr.ac.ucr.ie.Lonjevus.service.ResidentService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /*@RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }*/
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

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
            String fileName = photo.getOriginalFilename();
            Path path = Paths.get("uploads/residentes");

            try {
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                Path filePath = path.resolve(fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {

            }
            resident.setPhoto(fileName);
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

    @PostMapping("/updateResident")
    public String updateResident(@RequestBody Resident r) {
        if (r.getId() != 0) {
            System.out.println("\nLOS DATOS DEL RESIDENTE\n" + r.toString());
            ResidentService.update(r);

            return "Residente actualizado";
        }
        return "El residente no existe";
    }

    @GetMapping("/getContacts")
    public List<ResidentContact> getContacts(@RequestParam int id) {
        return ResidentContactService.getAll(id);
    }
}
