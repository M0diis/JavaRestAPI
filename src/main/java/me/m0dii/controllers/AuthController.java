package me.m0dii.controllers;

import me.m0dii.models.ERole;
import me.m0dii.models.Role;
import me.m0dii.models.User;
import me.m0dii.payload.request.LoginRequest;
import me.m0dii.payload.request.SignupRequest;
import me.m0dii.payload.response.MessageResponse;
import me.m0dii.payload.response.UserInfoResponse;
import me.m0dii.repository.RoleRepository;
import me.m0dii.repository.UserRepository;
import me.m0dii.security.jwt.JwtUtils;
import me.m0dii.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    AuthenticationManager authenticationManager;
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public AuthController(UserRepository userRepository, RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;

    /**
     * @param loginRequest Login request
     * @return {@link ResponseEntity} with {@link UserInfoResponse}
     *
     * @see LoginRequest
     */
    @PostMapping({"/signin", "/login"})
    public ResponseEntity<UserInfoResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(jwtCookie.toString(),
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    /**
     * @param signUpRequest Sign up request
     * @return {@link ResponseEntity} with {@link MessageResponse}
     *
     * @see SignupRequest
     */
    @PostMapping({"/signup", "/register"})
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
    {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             encoder.encode(signUpRequest.getPassword()));
        
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
    
        RuntimeException roleNotFound = new RuntimeException("Error: Role is not found.");
        
        if (strRoles == null)
        {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> roleNotFound);
            
            roles.add(userRole);
        }
        else
        {
            strRoles.forEach(role ->
            {
                switch(role)
                {
                    case "admin" -> {
                        Role adminRole =roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> roleNotFound);
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_MOD).orElseThrow(() -> roleNotFound);
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> roleNotFound);
                        roles.add(userRole);
                    }
                }
            });
        }
        
        user.setRoles(roles);
        userRepository.save(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * @return {@link ResponseEntity} with {@link MessageResponse}
     */
    @PostMapping({"/signout", "/logout", "/logoff"})
    public ResponseEntity<MessageResponse> logoutUser()
    {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Successfully logged out"));
    }
}