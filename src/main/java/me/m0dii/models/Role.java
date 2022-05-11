package me.m0dii.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    /**
     * Returns the id of the role.
     *
     * @return role id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the role.
     *
     * @param id role id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the role as an enum.
     *
     * @return role enum
     * @see #getRoleName()
     */
    public ERole getName() {
        return name;
    }

    /**
     * Sets the role enum.
     *
     * @param role enum
     */
    public void setName(ERole role) {
        this.name = role;
    }

    /**
     * Returns the name of the role as a string.
     *
     * @return role string
     * @see #getName()
     */
    public String getRoleName() {
        return name.getName();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
