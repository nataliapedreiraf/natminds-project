package es.natalia.natminds.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para permitir solicitudes CORS (Cross-Origin Resource Sharing) en la aplicación.
 * Habilita el acceso desde el origen "http://localhost:3000" para los métodos HTTP GET, POST, PUT y
 * DELETE, permitiendo el uso de credenciales y cualquier encabezado.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

  /**
   * Agrega configuraciones de mapeo CORS al registro.
   *
   * @param registry El registro CORS al que se agregarán las configuraciones.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000") // Origen permitido
        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
        .allowCredentials(true) // Permitir el uso de credenciales (cookies)
        .allowedHeaders("*"); // Permitir cualquier encabezado
  }
}
