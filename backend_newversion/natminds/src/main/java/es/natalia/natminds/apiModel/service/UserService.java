package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.model.model.User;
import java.util.List;
import java.util.Optional;
import javax.management.InstanceNotFoundException;

/** Interfaz que define el contrato para gestionar operaciones relacionadas con usuarios. */
public interface UserService {

  /**
   * Obtiene el usuario con el ID especificado.
   *
   * @param userId El ID del usuario que se desea obtener.
   * @return El usuario con el ID especificado.
   */
  public User getUser(Long userId);

  /**
   * Actualiza la información del usuario con el ID especificado.
   *
   * @param userId El ID del usuario que se desea actualizar.
   * @param user La información actualizada del usuario.
   * @return Una instancia opcional del usuario actualizado.
   * @throws InstanceNotFoundException Si no se encuentra la instancia del usuario.
   */
  public Optional<User> updateUser(Long userId, User user) throws InstanceNotFoundException;

  /**
   * Actualiza parcialmente la información del usuario con el ID especificado.
   *
   * @param userId El ID del usuario que se desea actualizar parcialmente.
   * @param user La información parcialmente actualizada del usuario.
   * @return El usuario con la información actualizada parcialmente.
   * @throws InstanceNotFoundException Si no se encuentra la instancia del usuario.
   */
  public User partialUpdateUser(Long userId, User user) throws InstanceNotFoundException;

  /**
   * Crea un nuevo usuario.
   *
   * @param user El usuario que se va a crear.
   * @return El usuario recién creado.
   */
  public User createUser(User user);

  /**
   * Elimina al usuario con el ID especificado.
   *
   * @param userId El ID del usuario que se desea eliminar.
   */
  public void removeUser(Long userId);

  /**
   * Busca usuarios con los parámetros proporcionados.
   *
   * @param name El nombre del usuario a buscar.
   * @param lastName El apellido del usuario a buscar.
   * @param userName El nombre de usuario a buscar.
   * @param email El correo electrónico del usuario a buscar.
   * @param biography La biografía del usuario a buscar.
   * @return Lista de usuarios que coinciden con los parámetros de búsqueda.
   */
  public List<User> findUsers(
      String name, String lastName, String userName, String email, String biography);

  /**
   * Autentica al usuario con el correo electrónico y la contraseña proporcionados.
   *
   * @param email El correo electrónico del usuario.
   * @param password La contraseña del usuario.
   * @return El usuario autenticado, o null si la autenticación falla.
   */
  public User authenticateUser(String email, String password);

  /**
   * Establece una relación de seguir entre el usuario seguidor y el usuario seguido.
   *
   * @param followerId El ID del usuario seguidor.
   * @param followingId El ID del usuario seguido.
   * @return El usuario seguidor con la relación actualizada.
   * @throws InstanceNotFoundException Si no se encuentra la instancia de alguno de los usuarios.
   */
  User followUser(Long followerId, Long followingId) throws InstanceNotFoundException;

  /**
   * Elimina la relación de seguir entre el usuario seguidor y el usuario seguido.
   *
   * @param followerId El ID del usuario seguidor.
   * @param followingId El ID del usuario seguido.
   * @return El usuario seguidor con la relación actualizada.
   * @throws InstanceNotFoundException Si no se encuentra la instancia de alguno de los usuarios.
   */
  User unfollowUser(Long followerId, Long followingId) throws InstanceNotFoundException;

  /**
   * Obtiene una lista de todos los usuarios.
   *
   * @return Lista de todos los usuarios.
   */
  public List<User> findAll();

  /**
   * Obtiene una lista de usuarios por su ID de usuario.
   *
   * @param userId El ID del usuario por el cual se desea obtener la lista.
   * @return Lista de usuarios que tienen el ID de usuario especificado.
   */
  public List<User> findUsersByUserId(Long userId);

  /**
   * Obtiene una lista de usuarios a seguir para el usuario actual.
   *
   * @param currentUserId El ID del usuario actual.
   * @return Lista de usuarios que el usuario actual puede seguir.
   */
  public List<User> getUsersToFollow(Long currentUserId);
}
