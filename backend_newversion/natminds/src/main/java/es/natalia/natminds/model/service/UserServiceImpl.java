package es.natalia.natminds.model.service;

import static es.natalia.natminds.model.repository.UserSpecification.*;

import es.natalia.natminds.apiModel.service.UserService;
import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import javax.management.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/** Implementación del servicio de usuarios. */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  /**
   * Crea un nuevo usuario.
   *
   * @param user Datos del nuevo usuario.
   * @return El usuario creado.
   */
  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Obtiene un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @return El usuario correspondiente al ID.
   */
  @Override
  public User getUser(Long userId) {
    Optional<User> userEntity = userRepository.findById(userId);
    return userEntity.get();
  }

  /**
   * Actualiza un usuario existente.
   *
   * @param userId ID del usuario a actualizar.
   * @param userEntity Datos actualizados del usuario.
   * @return El usuario actualizado.
   * @throws InstanceNotFoundException Si el usuario no existe.
   */
  @Override
  public Optional<User> updateUser(Long userId, User userEntity) throws InstanceNotFoundException {
    Optional<User> userEntity1 = userRepository.findById(userId);
    if (userEntity1.isEmpty()) throw new InstanceNotFoundException();
    User user = userEntity1.get();
    user.setUserName(userEntity.getUserName());
    user.setName(userEntity.getName());
    user.setLastName(userEntity.getLastName());
    user.setEmail(userEntity.getEmail());
    user.setPassword(userEntity.getPassword());
    user.setBiography(userEntity.getBiography());
    return userEntity1;
  }

  /**
   * Actualiza parcialmente un usuario existente.
   *
   * @param userId ID del usuario a actualizar.
   * @param newUser Datos actualizados del usuario.
   * @return El usuario actualizado.
   * @throws InstanceNotFoundException Si el usuario no existe.
   */
  @Override
  public User partialUpdateUser(Long userId, User newUser) throws InstanceNotFoundException {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) throw new InstanceNotFoundException();

    User user = userOptional.get();

    if (newUser.getName() != null) {
      user.setName(newUser.getName());
    }
    if (newUser.getLastName() != null) {
      user.setLastName(newUser.getLastName());
    }
    if (newUser.getUserName() != null) {
      user.setUserName(newUser.getUserName());
    }
    if (newUser.getEmail() != null) {
      user.setEmail(newUser.getEmail());
    }
    if (newUser.getPassword() != null) {
      user.setPassword(newUser.getPassword());
    }
    if (newUser.getBiography() != null) {
      user.setBiography(newUser.getBiography());
    }
    return user;
  }

  /**
   * Elimina un usuario por su ID.
   *
   * @param userId ID del usuario a eliminar.
   */
  @Override
  public void removeUser(Long userId) {
    userRepository.deleteById(userId);
  }

  /**
   * Busca usuarios con filtros opcionales.
   *
   * @param name Nombre del usuario.
   * @param lastName Apellido del usuario.
   * @param userName Nombre de usuario del usuario.
   * @param email Correo electrónico del usuario.
   * @param biography Biografía del usuario.
   * @return Lista de usuarios que cumplen con los criterios de búsqueda.
   */
  @Override
  public List<User> findUsers(
      String name, String lastName, String userName, String email, String biography) {
    Specification<User> filters =
        Specification.where(name == null ? null : byName(name))
            .and(lastName == null ? null : byLastName(lastName))
            .and(userName == null ? null : byUserName(userName))
            .and((email == null) ? null : byEmail(email))
            .and((biography == null) ? null : byBiography(biography));

    return userRepository.findAll(filters).stream().toList();
  }

  /**
   * Autentica a un usuario por su correo electrónico y contraseña.
   *
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return El usuario autenticado, o null si las credenciales son inválidas.
   */
  @Override
  public User authenticateUser(String email, String password) {

    Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

    return optionalUser.orElse(null);
  }

  /**
   * Permite a un usuario seguir a otro usuario.
   *
   * @param followerId ID del usuario que sigue.
   * @param followingId ID del usuario seguido.
   * @return El usuario que sigue actualizado.
   * @throws InstanceNotFoundException Si alguno de los usuarios no existe.
   */
  @Override
  public User followUser(Long followerId, Long followingId) throws InstanceNotFoundException {
    Optional<User> followerOptional = userRepository.findById(followerId);
    Optional<User> followingOptional = userRepository.findById(followingId);

    if (followerOptional.isEmpty() || followingOptional.isEmpty()) {
      throw new InstanceNotFoundException("Usuario no encontrado");
    }

    User follower = followerOptional.get();
    User following = followingOptional.get();

    follower.followUser(following);

    return follower;
  }

  /**
   * Permite a un usuario dejar de seguir a otro usuario.
   *
   * @param followerId ID del usuario que deja de seguir.
   * @param followingId ID del usuario dejado de seguir.
   * @return El usuario que deja de seguir actualizado.
   * @throws InstanceNotFoundException Si alguno de los usuarios no existe.
   */
  @Override
  public User unfollowUser(Long followerId, Long followingId) throws InstanceNotFoundException {
    Optional<User> followerOptional = userRepository.findById(followerId);
    Optional<User> followingOptional = userRepository.findById(followingId);

    if (followerOptional.isEmpty() || followingOptional.isEmpty()) {
      throw new InstanceNotFoundException("Usuario no encontrado");
    }

    User follower = followerOptional.get();
    User following = followingOptional.get();

    follower.unfollowUser(following);
    userRepository.save(follower);

    return follower;
  }

  /**
   * Obtiene una lista de todos los usuarios.
   *
   * @return Lista de usuarios.
   */
  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * Busca usuarios por su ID.
   *
   * @param userId ID del usuario.
   * @return Lista de usuarios que cumplen con el ID proporcionado.
   */
  @Override
  public List<User> findUsersByUserId(Long userId) {
    return null;
  }

  /**
   * Obtiene una lista de usuarios a seguir por un usuario específico.
   *
   * @param currentUserId ID del usuario actual.
   * @return Lista de usuarios a seguir.
   */
  public List<User> getUsersToFollow(Long currentUserId) {

    return userRepository.findUsersToFollow(currentUserId);
  }
}
