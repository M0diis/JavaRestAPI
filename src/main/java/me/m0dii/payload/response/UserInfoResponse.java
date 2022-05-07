package me.m0dii.payload.response;

import java.util.List;

public class UserInfoResponse
{
    private String token;

    private Long id;
    private String username;
    private String email;
    
    private final List<String> roles;
    
    public UserInfoResponse(String jwt, Long id, String username, String email, List<String> roles)
    {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public List<String> getRoles()
    {
        return roles;
    }
}