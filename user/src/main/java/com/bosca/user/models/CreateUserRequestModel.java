package com.bosca.user.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

    @NotNull(message = "Email cannot be null.")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 6, max = 16, message = "Password must be equal or greater than 6 characters and less than 16 characters.")
    private String password;

    @NotNull(message = "Username cannot be null.")
    @Size(min = 2, message = "Username must not be less than two characters.")
    private String username;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
