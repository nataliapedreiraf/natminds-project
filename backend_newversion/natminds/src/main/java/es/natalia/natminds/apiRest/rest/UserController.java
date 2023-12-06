package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiModel.service.UserService;
import es.natalia.natminds.apiRest.dto.LoginDto;
import es.natalia.natminds.apiRest.dto.UserDto;
import es.natalia.natminds.model.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.management.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controlador REST para gestionar operaciones relacionadas con usuarios. */
@CrossOrigin
@RestController
public class UserController {

  @Autowired private UserService userService;

  /**
   * Maneja la solicitud para crear un nuevo usuario.
   *
   * @param user Datos del nuevo usuario.
   * @return ResponseEntity con el estado de la creación.
   */
  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
    userService.createUser(user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Obtiene la información de un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @return ResponseEntity con la información del usuario y el estado de la solicitud.
   */
  @GetMapping("/users/{userId}")
  public ResponseEntity<User> getUser(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
  }

  /**
   * Actualiza la información de un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @param user Datos actualizados del usuario.
   * @return ResponseEntity con el estado de la actualización.
   * @throws InstanceNotFoundException Si no se encuentra el usuario.
   */
  @PutMapping("/users/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid User user)
      throws InstanceNotFoundException {
    userService.updateUser(userId, user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Actualiza parcialmente la información de un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @param user Datos parcialmente actualizados del usuario.
   * @return ResponseEntity con el estado de la actualización parcial.
   * @throws InstanceNotFoundException Si no se encuentra el usuario.
   */
  @PatchMapping("/users/{userId}")
  public ResponseEntity<User> userPartialUpdate(
      @PathVariable Long userId, @RequestBody @Valid User user) throws InstanceNotFoundException {
    userService.partialUpdateUser(userId, user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Elimina un usuario por su ID.
   *
   * @param userId ID del usuario a eliminar.
   * @return ResponseEntity con el estado de la solicitud.
   */
  @DeleteMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<User> removeUser(@PathVariable Long userId) {
    userService.removeUser((userId));
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Filtra usuarios por varios parámetros.
   *
   * @param name Nombre del usuario.
   * @param lastName Apellido del usuario.
   * @param userName Nombre de usuario del usuario.
   * @param email Correo electrónico del usuario.
   * @param biography Biografía del usuario.
   * @return Lista de usuarios que cumplen con los criterios de filtrado.
   */
  @GetMapping("/users/filter")
  public List<User> findUsers(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) String userName,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String biography) {
    return userService.findUsers(name, lastName, userName, email, biography);
  }

  /**
   * Inicia sesión de usuario.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @param loginDto Datos de inicio de sesión del usuario.
   * @return ResponseEntity con la información del usuario y el estado de la solicitud.
   */
  @PostMapping("/login")
  public ResponseEntity<UserDto> login(
      HttpSession httpSession, @RequestBody @Valid LoginDto loginDto) {
    User authenticatedUser =
        userService.authenticateUser(loginDto.getEmail(), loginDto.getPassword());

    if (authenticatedUser != null) {
      System.out.println("User authenticated. User ID: " + authenticatedUser.getUserId());
      httpSession.setAttribute("userId", authenticatedUser.getUserId());

      UserDto userDto = convertToDTO(authenticatedUser);
      return new ResponseEntity<>(userDto, HttpStatus.OK);
    } else {
      System.out.println("Authentication failed for user with email: " + loginDto.getEmail());
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  /**
   * Cierra sesión de usuario.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @return ResponseEntity con el estado de la solicitud.
   */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpSession httpSession) {
    try {
      Long userId = (Long) httpSession.getAttribute("userId");
      httpSession.invalidate();

      if (userId != null) {
        System.out.println("Sesión cerrada para el usuario con ID: " + userId);
      } else {
        System.out.println("Intento de cierre de sesión para una sesión no iniciada");
      }

      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Error al cerrar la sesión: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Convierte un objeto User a UserDto.
   *
   * @param user Usuario a convertir.
   * @return UserDto convertido.
   */
  private UserDto convertToDTO(User user) {
    UserDto userDto = new UserDto();
    userDto.setUserId(user.getUserId());
    userDto.setName(user.getName());
    userDto.setLastName(user.getLastName());
    userDto.setUserName(user.getUserName());
    userDto.setEmail(user.getEmail());
    userDto.setBiography(user.getBiography());

    return userDto;
  }

  /**
   * Permite a un usuario seguir a otro por su ID.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario que sigue.
   * @param followingId ID del usuario a seguir.
   * @return ResponseEntity con el estado de la solicitud.
   */
  @GetMapping("/users/follow/{followingId}")
  public ResponseEntity<Void> followUser(HttpSession httpSession, @PathVariable Long followingId) {
    try {
      Long loggedInUserId = (Long) httpSession.getAttribute("userId");
      System.out.println("Está autenticado el usuario: " + loggedInUserId);

      if (loggedInUserId == null) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // No está autenticado
      }

      userService.followUser(loggedInUserId, followingId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (InstanceNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Permite a un usuario dejar de seguir a otro por su ID.
   *
   * @param followingId ID del usuario a dejar de seguir.
   * @param httpSession La sesión HTTP que contiene la información del usuario que deja de seguir.
   * @return ResponseEntity con el estado de la solicitud.
   */
  @GetMapping("/users/unfollow/{followingId}")
  public ResponseEntity<Void> unfollowUser(
      @PathVariable Long followingId, HttpSession httpSession) {
    try {
      Long loggedInUserId = (Long) httpSession.getAttribute("userId");

      if (loggedInUserId == null) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // No está autenticado
      }

      userService.unfollowUser(loggedInUserId, followingId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (InstanceNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Obtiene la lista de todos los usuarios.
   *
   * @return Lista de UserDto representando a todos los usuarios.
   */
  @GetMapping("/users/all")
  public List<UserDto> getAllUsers() {
    List<UserDto> usersDtos = new ArrayList<>();
    List<User> users = userService.findAll();

    for (User u : users) {
      usersDtos.add(convertToDTO(u));
    }

    return usersDtos;
  }

  /**
   * Obtiene la lista de todos los usuarios y verifica si el usuario autenticado los está siguiendo.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario autenticado.
   * @return Lista de UserDto representando a todos los usuarios con información de si el usuario
   *     autenticado los sigue.
   */
  @GetMapping("/users/allisfollowing")
  public List<UserDto> getAllUsersByUserId(HttpSession httpSession) {
    Long userId = (Long) httpSession.getAttribute("userId");

    List<UserDto> usersDtos = new ArrayList<>();
    List<User> users = userService.findAll();
    UserDto userDto;

    for (User u : users) {
      if (u.getUserId() != userId) {
        userDto = convertToDTO(u);
        userDto.setIsFollowing(u.getFollowers().contains(userService.getUser(userId)));
        usersDtos.add(userDto);
      }
    }

    return usersDtos;
  }

  /**
   * Obtiene la lista de usuarios a los que el usuario autenticado puede seguir.
   *
   * @param session La sesión HTTP que contiene la información del usuario autenticado.
   * @return Lista de UserDto representando a los usuarios que el usuario autenticado puede seguir.
   */
  @GetMapping("/tofollow")
  public List<UserDto> getUsersToFollow(HttpSession session) {
    Long currentUserId = (Long) session.getAttribute("userId");

    List<User> usersToFollow = userService.getUsersToFollow(currentUserId);

    List<UserDto> usersToFollowDto =
        usersToFollow.stream()
            .map(
                user -> {
                  UserDto userDto = new UserDto();
                  userDto.setUserId(user.getUserId());
                  userDto.setName(user.getName());
                  userDto.setLastName(user.getLastName());
                  userDto.setUserName(user.getUserName());
                  userDto.setEmail(user.getEmail());
                  userDto.setBiography(user.getBiography());

                  userDto.setIsFollowing(
                      user.getFollowers().stream()
                          .anyMatch(follower -> follower.getUserId().equals(currentUserId)));

                  return userDto;
                })
            .collect(Collectors.toList());

    return usersToFollowDto;
  }
}
