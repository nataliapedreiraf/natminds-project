package es.natalia.natminds.model.service;

import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.UserRepository;
import es.natalia.natminds.apiModel.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import static es.natalia.natminds.model.repository.UserSpecification.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        return userEntity.get();
    }

    @Override
    public Optional<User> updateUser(Long userId, User userEntity) throws InstanceNotFoundException {
        Optional<User> userEntity1 = userRepository.findById(userId);
        if(userEntity1.isEmpty()) throw new InstanceNotFoundException();
        User user = userEntity1.get();
        user.setUserName(userEntity.getUserName());
        user.setName(userEntity.getName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setBiography(userEntity.getBiography());
        return userEntity1;
    }

    @Override
    public User partialUpdateUser(Long userId, User newUser) throws InstanceNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new InstanceNotFoundException();

        User user = userOptional.get();

        if(newUser.getName() != null){
            user.setName(newUser.getName());
        }
        if(newUser.getLastName() != null){
            user.setLastName(newUser.getLastName());
        }
        if(newUser.getUserName() != null){
            user.setUserName(newUser.getUserName());
        }
        if(newUser.getEmail() != null){
            user.setEmail(newUser.getEmail());
        }
        if(newUser.getPassword() != null){
            user.setPassword(newUser.getPassword());
        }
        if(newUser.getBiography() != null){
            user.setBiography(newUser.getBiography());
        }
        return user;
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findUsers(String name, String lastName, String userName, String email, String biography) {
        Specification<User> filters = Specification.where(name == null ? null : byName(name))
                .and(lastName == null ? null : byLastName(lastName))
                .and(userName == null ? null : byUserName(userName))
                .and((email == null) ? null : byEmail(email))
                .and((biography == null) ? null : byBiography(biography));


        return userRepository.findAll(filters)
                .stream()
                .toList();
    }
@Override
    public User authenticateUser(String email, String password) {
        // Implementa la lógica para verificar las credenciales del usuario
        // Puedes utilizar el UserRepository para buscar el usuario por email y contraseña
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

        // Devuelve el usuario si existe y las credenciales son válidas
        return optionalUser.orElse(null);
    }

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
        //userRepository.save(follower);

        return follower;
    }

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

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByUserId(Long userId) {
        return null;
    }


}
