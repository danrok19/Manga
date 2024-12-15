package com.db.manga.entity.objsql.types;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class UserType {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "signup_date")
    private String signupDate;

    public UserType() {
    }

    public UserType(String username, String password, String email, String signupDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.signupDate = signupDate;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", signupDate='" + signupDate + '\'' +
                '}';
    }
}
