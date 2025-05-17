
package cr.ac.ucr.ie.Longevus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/photos/suppliers/**")            // URL pública ante el navegador
          .addResourceLocations("file:uploads/photos/suppliers/") // carpeta local
          .setCachePeriod(0);                            // opcional: sin caché
    }
    
}
