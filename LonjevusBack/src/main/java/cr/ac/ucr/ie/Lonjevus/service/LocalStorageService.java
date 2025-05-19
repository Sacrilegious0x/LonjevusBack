
package cr.ac.ucr.ie.Lonjevus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class LocalStorageService {
    
    private final String uploadDir = "C:/Users/Usuario/Desktop/UCR MEDIACION 2025 PRIMER CICLO/Lenguajes para aplicaciones comerciales/ProyectoLenguajes BACK/proyecto-lonjevus-back/LonjevusBack/uploads/photos/suppliers/";


    public String save(MultipartFile file) {
        try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // nombre unico
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath);

             return "photos/suppliers/" + fileName;// se muestra en la web
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
    
}
