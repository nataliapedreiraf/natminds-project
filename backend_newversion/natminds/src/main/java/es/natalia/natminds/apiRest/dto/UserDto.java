package es.natalia.natminds.apiRest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Long userId;
  private String name;
  private String lastName;
  private String userName;
  private String email;
  private String biography;
  private Boolean isFollowing;
}
