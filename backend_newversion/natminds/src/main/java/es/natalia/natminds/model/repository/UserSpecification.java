package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.User;
import org.springframework.data.jpa.domain.Specification;

/** Especificaciones de búsqueda para la entidad "User". */
public class UserSpecification {

  /**
   * Crea una especificación para buscar usuarios por nombre.
   *
   * @param name Nombre a buscar.
   * @return Especificación de búsqueda por nombre.
   */
  public static Specification<User> byName(String name) {
    return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
  }

  /**
   * Crea una especificación para buscar usuarios por apellido.
   *
   * @param lastName Apellido a buscar.
   * @return Especificación de búsqueda por apellido.
   */
  public static Specification<User> byLastName(String lastName) {
    return (root, query, builder) -> builder.like(root.get("lastName"), "%" + lastName + "%");
  }

  /**
   * Crea una especificación para buscar usuarios por nombre de usuario.
   *
   * @param userName Nombre de usuario a buscar.
   * @return Especificación de búsqueda por nombre de usuario.
   */
  public static Specification<User> byUserName(String userName) {
    return (root, query, builder) -> builder.like(root.get("userName"), "%" + userName + "%");
  }

  /**
   * Crea una especificación para buscar usuarios por correo electrónico.
   *
   * @param email Correo electrónico a buscar.
   * @return Especificación de búsqueda por correo electrónico.
   */
  public static Specification<User> byEmail(String email) {
    return (root, query, builder) -> builder.like(root.get("email"), "%" + email + "%");
  }

  /**
   * Crea una especificación para buscar usuarios por biografía.
   *
   * @param biography Biografía a buscar.
   * @return Especificación de búsqueda por biografía.
   */
  public static Specification<User> byBiography(String biography) {
    return (root, query, builder) -> builder.like(root.get("biography"), "%" + biography + "%");
  }
}
