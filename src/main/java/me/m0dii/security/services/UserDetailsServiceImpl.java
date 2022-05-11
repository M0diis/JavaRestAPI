package me.m0dii.security.services;

import me.m0dii.models.User;
import me.m0dii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    /**
     * Loads the user by the username.
     *
     * @param username the username
     * @return the user details
     *
     * @throws UsernameNotFoundException if the user by username is not found
     *
     * @see UserDetailsService#loadUserByUsername(String)
     * @see UserDetails
     * @see User
     *
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        
        return UserDetailsImpl.build(user);
    }
}