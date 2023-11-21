package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.User;
import org.springframework.data.jpa.domain.Specification;

/*Creamos la clase Specification para el user. Aquí vamos a definir una serie de especificaciones que luego usaremos
* para poder hacer búsquedas filtradas por atributos. Utilizamos funciones lambda para definir aquello por lo que
* vamos a querer filtrar y que por tanto necesitaremos comparar con la información que hay en BBDD.*/
public class UserSpecification {
    public UserSpecification(){}

    public static Specification<User> byName(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<User> byLastName(String lastName) {
        return (root, query, builder) -> {
            return builder.like(root.get("lastName"), "%" + lastName + "%");
        };
    }

    public static Specification<User> byUserName(String userName) {
        return (root, query, builder) -> {
            return builder.like(root.get("userName"), "%" + userName + "%");
        };
    }

    public static Specification<User> byEmail(String email) {
        return (root, query, builder) -> {
            return builder.like(root.get("email"), "%" + email + "%");
        };
    }

    public static Specification<User> byBiography(String biography) {
        return (root, query, builder) -> {
            return builder.like(root.get("biography"), "%" + biography + "%");
        };
    }
}
