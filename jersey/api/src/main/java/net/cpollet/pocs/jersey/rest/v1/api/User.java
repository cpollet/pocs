package net.cpollet.pocs.jersey.rest.v1.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Christophe Pollet
 */
@XmlRootElement
public class User {
    private String username;
    private String email;
    private boolean admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                '}';
    }
}
