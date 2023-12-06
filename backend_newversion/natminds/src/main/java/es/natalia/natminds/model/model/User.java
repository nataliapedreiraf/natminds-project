package es.natalia.natminds.model.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

/**
 * Clase de modelo que representa la entidad "User" para gestionar la información de los usuarios.
 */
@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastName;

    /**
     * Nombre de usuario único. Se utiliza para identificar al usuario.
     */
    @Column(unique = true)
    private String userName;

    /**
     * Dirección de correo electrónico del usuario.
     */
    @Email
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Biografía del usuario.
     */
    private String biography;

    /**
     * Conjunto de usuarios que siguen a este usuario.
     */
    @ManyToMany
    @JoinTable(
        name = "followers",
        joinColumns = @JoinColumn(name = "following_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    /**
     * Conjunto de usuarios a los que este usuario sigue.
     */
    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    // Constructor, getters, setters y otros métodos

    /**
     * Método para que este usuario siga a otro usuario.
     *
     * @param userToFollow Usuario a seguir.
     */
    public void followUser(User userToFollow) {
        System.out.println("Following user: " + userToFollow.getUserName());
        this.following.add(userToFollow);
        userToFollow.getFollowers().add(this);
    }

    /**
     * Método para dejar de seguir a otro usuario.
     *
     * @param userToUnfollow Usuario a dejar de seguir.
     */
    public void unfollowUser(User userToUnfollow) {
        this.following.remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(this);
    }

    /**
     * Sobrescritura del método equals para comparar usuarios por su identificador único.
     *
     * @param o Objeto a comparar.
     * @return true si los usuarios son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    /**
     * Sobrescritura del método hashCode para generar un código hash basado en el identificador único.
     *
     * @return Código hash del usuario.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}

