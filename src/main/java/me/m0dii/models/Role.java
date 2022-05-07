package me.m0dii.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private int id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    
    public Role()
    {
    
    }
    
    public Role(ERole name)
    {
        this.name = name;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public ERole getName()
    {
        return name;
    }
    
    public void setName(ERole role)
    {
        this.name = role;
    }
    
    public String getRoleName()
    {
        return name.getName();
    }
    
    @Override
    public String toString()
    {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
