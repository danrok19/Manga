package com.db.manga.entity.sql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "role_description")
    private String roleDescription;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Role() {}

    public Role(String id, String roleDescription) {
        this.id = id;
        this.roleDescription = roleDescription;
    }

    public void add(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}
