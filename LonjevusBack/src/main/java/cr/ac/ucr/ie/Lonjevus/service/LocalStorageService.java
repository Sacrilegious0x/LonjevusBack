package cr.ac.ucr.ie.Lonjevus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalStorageService {

    private final String uploadDir = "uploads/photos/suppliers";

    public String save(MultipartFile file) {
        try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Crear nombre único
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar archivo (reemplaza si ya existe)
            Files.copy(file.getInputStream(), filePath);

            // Retornar ruta relativa para guardar en BD o servir en web
            return "photos/suppliers/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
}
