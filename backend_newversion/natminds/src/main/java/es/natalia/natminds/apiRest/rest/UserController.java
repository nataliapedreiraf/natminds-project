package es.natalia.natminds.apiRest.rest;


import es.natalia.natminds.apiModel.service.UserService;
import es.natalia.natminds.apiRest.dto.LoginDto;
import es.natalia.natminds.apiRest.dto.UserDto;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceNotFoundException;
import java.util.List;
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // POST
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid User user) throws InstanceNotFoundException {
        userService.updateUser(userId, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<User> userPartialUpdate(@PathVariable Long userId,
                                             @RequestBody @Valid User user) throws InstanceNotFoundException {
        userService.partialUpdateUser(userId, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<User> removeUser (@PathVariable Long userId){
        userService.removeUser((userId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/filter")
    public List<User> findUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String biography){

        return  userService.findUsers(name, lastName, userName, email, biography);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(HttpSession httpSession, @RequestBody @Valid LoginDto loginDto) {
        User authenticatedUser = userService.authenticateUser(loginDto.getEmail(), loginDto.getPassword());

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

    private UserDto convertToDTO(User user) {
        // Implementa la lógica para convertir User a UserDTO
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setBiography(user.getBiography());
        // Puedes agregar más campos según sea necesario

        return userDto;
    }

    @PostMapping("/users/{followerId}/follow/{followingId}")
    public ResponseEntity<Void> followUser(@PathVariable Long followerId, @PathVariable Long followingId, HttpSession httpSession) {
        try {
            Long loggedInUserId = (Long) httpSession.getAttribute("userId");

            if (loggedInUserId == null || !loggedInUserId.equals(followerId)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // No coincide con el usuario autenticado
            }

            userService.followUser(followerId, followingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/{followerId}/unfollow/{followingId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId, HttpSession httpSession) {
        try {
            Long loggedInUserId = (Long) httpSession.getAttribute("userId");

            if (loggedInUserId == null || !loggedInUserId.equals(followerId)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // No coincide con el usuario autenticado
            }

            userService.unfollowUser(followerId, followingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
}
