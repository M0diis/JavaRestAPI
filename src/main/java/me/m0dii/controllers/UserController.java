package me.m0dii.controllers;

import me.m0dii.advices.UserNotFoundException;
import me.m0dii.models.User;
import me.m0dii.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController
{
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }
    
    @PostMapping("/create")
    public User createUser(User user)
    {
        return userRepository.save(user);
    }
    
    @GetMapping("/get/{id}")
    public User getUser(@PathVariable Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @PutMapping("/put/{id}")
    public User updateUser(@PathVariable Long id, User user)
    {
        return userRepository.findById(id).map(u ->
        {
            u.setEmail(user.getEmail());
            return userRepository.save(u);
        }).orElseGet(() ->
        {
            user.setId(id);
            return userRepository.save(user);
        });
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userRepository.deleteById(id);
    }
}


