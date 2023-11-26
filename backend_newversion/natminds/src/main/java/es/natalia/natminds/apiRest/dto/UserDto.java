package es.natalia.natminds.apiRest.dto;

import lombok.Data;

@Data
public class UserDto {
  private Long userId;
  private String name;
  private String lastName;
  private String userName;
  private String email;
  private String biography;
}
