package org.example;

public class UserChange {
    private String email;
    private String password;
    private String name;

    public UserChange(String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserChange() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
