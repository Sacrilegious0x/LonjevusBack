/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author User
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePath = "/photos/**";
        String resourceLocation = "file:C:/Users/User/Desktop/"
                + "Proyecto Lenguajes/proyecto-lonjevus-back/LonjevusBack/uploads/photos/";
        
        registry.addResourceHandler(resourcePath).addResourceLocations(resourceLocation);
    }
    
}
