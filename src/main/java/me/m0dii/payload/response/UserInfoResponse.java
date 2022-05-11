package me.m0dii.payload.response;

import java.util.List;

public class UserInfoResponse {
    private final List<String> roles;
    private String token;
    private Long id;
    private String username;
    private String email;

    public UserInfoResponse(String jwt, Long id, String username, String email, List<String> roles) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Returns the user's ID in the response.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID in the response.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's email in the response.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email in the response.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's username in the response.
     *
     * @return the username
     * @see #setUsername(String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username in the response.
     *
     * @param username the username
     * @see #getUsername()
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's JWT token in the response.
     *
     * @return the token
     * @see #setToken(String)
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the user's JWT token in the response.
     *
     * @param token the token
     * @see #getToken()
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the user's roles in the response.
     *
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }
}