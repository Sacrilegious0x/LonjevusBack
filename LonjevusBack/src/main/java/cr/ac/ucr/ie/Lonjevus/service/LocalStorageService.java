<<<<<<< HEAD

package cr.ac.ucr.ie.Lonjevus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

>>>>>>> gabriel
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
<<<<<<< HEAD


@Service
public class LocalStorageService {
    
    private final String uploadDir = "C:/Users/Usuario/Desktop/UCR MEDIACION 2025 PRIMER CICLO/Lenguajes para aplicaciones comerciales/ProyectoLenguajes BACK/proyecto-lonjevus-back/LonjevusBack/uploads/photos/suppliers/";


    public String save(MultipartFile file) {
        try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
=======
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
public class LocalStorageService {
   
    
    public String saveAdminPhoto(MultipartFile file) throws IOException {
           String uploadDirA = "uploads/photos/admin";
    try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDirA);
>>>>>>> gabriel
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // nombre unico
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath);

<<<<<<< HEAD
             return "photos/suppliers/" + fileName;// se muestra en la web
=======
             return "photos/admin/" + fileName;// se muestra en la web
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }

    }
    
    public String saveCaregiverPhoto(MultipartFile file){
        String uploadDirA = "uploads/photos/caregiver";
    try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDirA);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // nombre unico
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath);

             return "photos/caregiver/" + fileName;// se muestra en la web
>>>>>>> gabriel
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
    
}
