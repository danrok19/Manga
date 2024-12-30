package com.db.manga.entity.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    private String username;
    private String password;
    private String email;
    private String role;
    private String signupDate;
    private List<ObjectId> subscriptions;

    public User() {}

    public User(String username, String password, String email, String role, String signupDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.signupDate = signupDate;
    }

    public void add(Subscription subscription){
        if(subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        subscriptions.add(subscription.getId());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public List<ObjectId> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<ObjectId> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", signupDate='" + signupDate + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
