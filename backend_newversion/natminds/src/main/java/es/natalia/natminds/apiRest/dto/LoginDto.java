package es.natalia.natminds.apiRest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
  @Email
  private String email;

  @NotBlank
  private String password;
}
