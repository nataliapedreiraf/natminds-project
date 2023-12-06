package es.natalia.natminds.apiRest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Clase de transferencia de datos (DTO) utilizada para representar información de usuarios. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  /** ID del usuario. */
  private Long userId;

  /** Nombre del usuario. */
  private String name;

  /** Apellido del usuario. */
  private String lastName;

  /** Nombre de usuario del usuario. */
  private String userName;

  /** Correo electrónico del usuario. */
  private String email;

  /** Biografía del usuario. */
  private String biography;

  /** Indica si el usuario está siendo seguido. */
  private Boolean isFollowing;
}
