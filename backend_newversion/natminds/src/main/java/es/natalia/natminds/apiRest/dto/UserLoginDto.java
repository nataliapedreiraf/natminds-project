package es.natalia.natminds.apiRest.dto;

import lombok.Data;

/** Clase de transferencia de datos (DTO) utilizada para el inicio de sesión de usuarios. */
@Data
public class UserLoginDto {

  /** Correo electrónico del usuario para el inicio de sesión. */
  private String email;

  /** Contraseña del usuario para el inicio de sesión. */
  private String password;
}
