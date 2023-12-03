package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.model.model.User;
import java.util.Optional;
import javax.management.InstanceNotFoundException;
import java.util.List;

public interface UserService {
    public User getUser(Long userId);
    public Optional<User> updateUser(Long userId, User user) throws InstanceNotFoundException;
    public User partialUpdateUser(Long userId, User user) throws InstanceNotFoundException;
    public User createUser(User user);
    public void removeUser(Long userId);
    public List<User> findUsers(String name, String lastName, String userName, String email, String biography);

    public User authenticateUser(String email, String password);

    User followUser(Long followerId, Long followingId) throws InstanceNotFoundException;

    User unfollowUser(Long followerId, Long followingId) throws InstanceNotFoundException;

    public List<User> findAll();

    public List<User> findUsersByUserId(Long userId);

    public List<User> getUsersToFollow(Long currentUserId);
}
