package cr.ac.ucr.ie.Lonjevus;

import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import cr.ac.ucr.ie.Lonjevus.repository.IAdminRepository;
import java.lang.reflect.Field;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LonjevusApplication {

	public static void main(String[] args) {
		SpringApplication.run(LonjevusApplication.class, args);
	}
//@Bean
//	public CommandLineRunner createTestAdmin(IAdminRepository adminRepo, PasswordEncoder passwordEncoder) {
//		return args -> {
//			String testEmail = "test@lonjevus.com";
//
//			// 1. Revisa si el usuario de prueba ya existe para no crearlo múltiples veces.
//			if (adminRepo.findByEmail(testEmail).isEmpty()) {
//				System.out.println("\n--- Creando usuario de prueba 'test@lonjevus.com' ---\n");
//				
//				Admin testAdmin = new Admin();
//				testAdmin.setEmail(testEmail);
//				
//				// 2. Hashea la contraseña directamente con el PasswordEncoder de la aplicación.
//				testAdmin.setPassword(passwordEncoder.encode("password123"));
//				testAdmin.setName("Admin de Prueba");
//                                testAdmin.setEmail("testAdmin@gmail.com");
//				testAdmin.setIdentification("000000000");
//				testAdmin.setIsActive(true);
//				// ... puedes setear otros campos si son obligatorios en tu entidad ...
//
//				// 3. Guarda el nuevo administrador a través del repositorio.
//				adminRepo.save(testAdmin);
//				
//				System.out.println("\n--- ¡Usuario de prueba creado exitosamente! ---\n");
//			} else {
//				System.out.println("\n--- El usuario de prueba 'test@lonjevus.com' ya existe. No se ha creado de nuevo. ---\n");
//			}
//		};
//	}
}
