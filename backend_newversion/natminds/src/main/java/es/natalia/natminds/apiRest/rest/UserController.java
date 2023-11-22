package es.natalia.natminds.apiRest.rest;


import es.natalia.natminds.apiModel.service.UserService;
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
    public ResponseEntity<User> login(HttpSession httpSession, @RequestBody @Valid User user) {

        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (authenticatedUser != null) {
            httpSession.setAttribute("userId", authenticatedUser.getUserId());
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
