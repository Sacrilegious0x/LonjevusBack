/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
public class LocalStorageService {
    private final String uploadDirA = "uploads/photos/admin";
    
    public String saveAdminPhoto(MultipartFile file) throws IOException {
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

             return "photos/admin/" + fileName;// se muestra en la web
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }

}
    
}
