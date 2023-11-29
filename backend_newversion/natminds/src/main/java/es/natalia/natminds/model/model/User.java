package es.natalia.natminds.model.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String userName;

    @Email
    private String email;

    private String password;
    private String biography;

    @ManyToMany
    @JoinTable(
        name = "followers",
        joinColumns = @JoinColumn(name = "following_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    // Constructor, getters, setters y otros métodos

    public void followUser(User userToFollow) {
        System.out.println("Following user: " + userToFollow.getUserName());
        this.following.add(userToFollow);
        userToFollow.getFollowers().add(this);
    }

    public void unfollowUser(User userToUnfollow) {
        this.following.remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}

