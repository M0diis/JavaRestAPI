package me.m0dii.controllers;
import me.m0dii.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController
{
    private final UserRepository userRepository;
    
    public TestController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/all")
    public String allAccess()
    {
        return "Public content.";
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'MOD','ADMIN')")
    public String userAccess()
    {
        return "User (logged-in) only content.";
    }
    
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MOD')")
    public String moderatorAccess()
    {
        return "Moderator content.";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess()
    {
        return "Admin content.";
    }
}
