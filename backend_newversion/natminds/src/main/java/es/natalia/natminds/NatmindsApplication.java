package es.natalia.natminds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Clase principal de la aplicación Natminds. Configura y ejecuta la aplicación Spring Boot. */
@SpringBootApplication
public class NatmindsApplication {

  /**
   * Método principal que inicia la aplicación Spring Boot.
   *
   * @param args Argumentos de la línea de comandos.
   */
  public static void main(String[] args) {
    SpringApplication.run(NatmindsApplication.class, args);
  }
}
