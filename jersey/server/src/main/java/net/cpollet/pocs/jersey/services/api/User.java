package net.cpollet.pocs.jersey.services.api;

import java.time.LocalDate;

/**
 * @author Christophe Pollet
 */
public class User {
    private final String username;
    private final String email;
    private final boolean admin;
    private final LocalDate joinDate;

    public User(boolean admin, String email, String username, LocalDate joinDate) {
        this.admin = admin;
        this.email = email;
        this.username = username;
        this.joinDate=joinDate;
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

    public LocalDate getJoinDate() {
        return joinDate;
    }
}
