package es.natalia.natminds.apiRest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Clase de transferencia de datos (DTO) utilizada para la autenticación de usuarios.
 */
@Data
public class LoginDto {

  /**
   * Correo electrónico del usuario para la autenticación.
   */
  @Email
  private String email;

  /**
   * Contraseña del usuario para la autenticación.
   */
  @NotBlank
  private String password;
}
