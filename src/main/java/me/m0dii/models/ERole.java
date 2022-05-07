package me.m0dii.models;

import java.util.Arrays;

public enum ERole
{
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MOD("ROLE_MOD"),
    ROLE_USER("ROLE_USER");
    
    private final String name;
    
    ERole(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public static ERole getRole(String name)
    {
        return Arrays.stream(ERole.values()).filter(role -> role.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
