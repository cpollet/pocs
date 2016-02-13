package net.cpollet.pocs.jersey.services.api;

/**
 * @author Christophe Pollet
 */
public class User {
    private final String username;
    private final String email;
    private final boolean admin;

    public User(boolean admin, String email, String username) {
        this.admin = admin;
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }
}
