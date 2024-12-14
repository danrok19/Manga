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
    private long id;

    @Column(name = "role_description")
    private String roleDescription;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Role() {}

    public Role(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public void add(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
